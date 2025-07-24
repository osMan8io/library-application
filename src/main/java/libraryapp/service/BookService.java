package libraryapp.service;

import libraryapp.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    Book searchById(Long id);
    List<Book> searchByCategory(String category);

}