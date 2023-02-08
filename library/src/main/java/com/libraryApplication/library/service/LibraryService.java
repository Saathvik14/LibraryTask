package com.libraryApplication.library.service;

import com.libraryApplication.library.model.Library;

import java.util.List;
import java.util.Optional;

public interface LibraryService {
    public Library addbook(Library library);

    public List<Library> getbook();

    public void deletebook(int bookId);


    public Library updateBook(int bookId, Library library);

    public Optional<Library> listById(int bookId);

    public List<Library> findByTitle(String bookTitle);

    public List<Library> findByGenre(String genre);

    public List<Library> findByAuthor(String author);
}
