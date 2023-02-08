package com.libraryApplication.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.libraryApplication.library.controller.LibraryController;
import com.libraryApplication.library.model.Library;
import com.libraryApplication.library.service.LibraryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LibraryControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    @Mock
    private LibraryService libraryService;

    @InjectMocks
    private LibraryController libraryController;


    Library lib_1=new Library(1,"Lotte","autobio","abdu");

    Library lib_2=new Library(2,"Chocos","autobio","abdu");


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(libraryController).build();
    }


    @Test
    public void addBook_success() throws Exception{
        Library record= Library.builder()
                .bookId(1)
                .bookTitle("Lotte")
                .genre("autobio")
                .author("abdu")
                .build();

        when(libraryService.addbook(record)).thenReturn(record);

        String content=objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder= MockMvcRequestBuilders.post("/book/addbook")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.bookTitle").value("Lotte"));
    }

    @Test
    public void getBook_success() throws Exception{
        List<Library> records=new ArrayList<>(Arrays.asList(lib_1,lib_2));

        when(libraryService.getbook()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/listallbook")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].bookTitle", is("Lotte")));

    }

    @Test
    public void deleteBook_success() throws Exception{
        willDoNothing().given(libraryService).deletebook(lib_2.getBookId());
        ResultActions resultActions = mockMvc.perform(delete("/book/delete/2" + lib_2.getBookId()));
        resultActions.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void updateBook_success() throws Exception{
        Library updateRecord= Library.builder()
                .bookId(1)
                .bookTitle("Lotte Choco")
                .genre("hello")
                .author("yo")
                .build();

        when(libraryService.updateBook(lib_1.getBookId(),updateRecord)).thenReturn(updateRecord);

        String updatedContent= objectWriter.writeValueAsString(updateRecord);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder=MockMvcRequestBuilders.put("/book/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.bookTitle").value("Lotte Choco"));


    }

    @Test
    public void getBookById_success() throws Exception{
        when(libraryService.listById(lib_1.getBookId())).thenReturn(java.util.Optional.of(lib_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/viewbyid/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.bookTitle").value("Lotte"));
    }

    @Test
    public void getBookByTitle_success() throws Exception{
        when(libraryService.findByTitle(lib_1.getBookTitle())).thenReturn(java.util.List.of(lib_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/listbook/Lotte")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));

    }

    @Test
    public void getBookByGenre_success() throws Exception{
        when(libraryService.findByGenre(lib_1.getBookTitle())).thenReturn(java.util.List.of(lib_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/listgenre/autobio")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));

    }

    @Test
    public void getBookByAuthor_success() throws Exception{
        when(libraryService.findByAuthor(lib_1.getBookTitle())).thenReturn(java.util.List.of(lib_1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/listauthor/abu")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));

    }

}
