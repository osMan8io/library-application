package libraryapp.service;

import libraryapp.entity.Book;
import libraryapp.entity.User;

import java.util.List;

public interface UserService {

    void borrowBook(Long userId, Long bookId);
    void returnBook(Long bookId);
    void updateUserPasswordById(Long userId, String newPassword);
    void deleteUserById(Long userId);

    User userLogin(String username, String password);
    User readUserById(Long userId);

    List<User> readAllUsers();
    List<Book> searchBooksByTitle(String title);
    List<Book> searchBooksByAuthor(String author);
    List<Book> searchBooksByCategory(String category);
    List<Book> readAllBooksByUserId(Long userId);

}
