package libraryapp.service;

import libraryapp.dto.UserDTO;
import libraryapp.entity.Book;
import libraryapp.entity.User;

import java.util.List;

public interface UserService {

    void borrowBook(Long userId, Long bookId);
    void returnBook(Long bookId);
    void updateUserPasswordById(Long userId, String newPassword);
    void deleteUserById(Long userId);
    void addUser(UserDTO userDTO);

    User userLogin(String username, String password);
    User readUserById(Long userId);

    User readUserByUsername(String username);
    List<User> readAllUsers();

}
