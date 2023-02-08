package com.libraryApplication.library.repository;

import com.libraryApplication.library.model.Library;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LibraryRepository extends MongoRepository<Library, Integer> {

    @Query("{bookTitle:?0}")
    List<Library> findByTitle(String bookTitle);

    @Query("{genre:?0}")
    List<Library> findByGenre(String genre);

    @Query("{author:?0}")
    List<Library> findByAuthor(String author);
}
