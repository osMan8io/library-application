package libraryapp.service;

import libraryapp.dto.BookDTO;
import libraryapp.entity.Book;

import java.util.List;

public interface BookService {

    void addBook(BookDTO bookDTO);
    void removeBook(Long bookId);

    Book searchById(Long id);
    Book updateBook(Long bookId, BookDTO bookDTO);

    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    List<Book> getAllBooks();
    List<Book> searchByCategory(String category);
    List<Book> getAvailableBooks();
    List<Book> search(String query);
}