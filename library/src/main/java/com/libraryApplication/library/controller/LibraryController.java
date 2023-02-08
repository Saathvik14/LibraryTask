package com.libraryApplication.library.controller;

import com.libraryApplication.library.LibraryApplication;
import com.libraryApplication.library.model.Library;
import com.libraryApplication.library.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class LibraryController {

    Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/addbook")
    public Library addbook(@RequestBody Library library) {
        logger.info("[addbook] info message added");
        return libraryService.addbook(library);

    }

    @GetMapping("/listallbook")
    public List<Library> getbook() {
        logger.info("[listallbook] info message added");
        return libraryService.getbook();
    }

    @GetMapping("/viewbyid/{bookId}")
    public Optional<Library> listbook(@PathVariable("bookId") int bookId) {
        logger.info("[viewbyid] info message added");
        return libraryService.listById(bookId);
    }


    @DeleteMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") int bookId) {
        logger.info("[delete] info message added");
        libraryService.deletebook(bookId);
        return "data deleted successfully";
    }

    @PutMapping("/update/{bookId}")
    public Library updateBook(@RequestBody Library library, @PathVariable("bookId") int bookId) {
        logger.info("[update] info message added");
        return libraryService.updateBook(bookId, library);
    }

    @GetMapping("/listbook/{bookTitle}")
    public List<Library> findByTitle(@PathVariable("bookTitle") String bookTitle) {
        logger.info("[listbook] info message added");
        return libraryService.findByTitle(bookTitle);
    }

    @GetMapping("/listgenre/{genre}")
    public List<Library> findByGenre(@PathVariable("genre") String genre) {
        logger.info("[listgenre] info message added");
        return libraryService.findByGenre(genre);
    }

    @GetMapping("/listauthor/{author}")
    public List<Library> findByAuthor(@PathVariable("author") String author) {
        logger.info("[listauthor] info message added");
        return libraryService.findByAuthor(author);
    }

}
