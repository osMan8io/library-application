package libraryapp.service;

import libraryapp.entity.Book;
import libraryapp.enums.Category;

import java.util.List;

public interface Searchable {

    Book searchByTitle(String title);
    Book searchByAuthor(String author);
    Book searchById(int id);
    List<Book> searchByCategory(String category);

}
