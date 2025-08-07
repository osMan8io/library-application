package libraryapp.repository;

import jakarta.transaction.Transactional;
import libraryapp.dto.BookDTO;
import libraryapp.dto.UserDTO;
import libraryapp.entity.Book;
import libraryapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserIdById(Long id);

    @Transactional
    @Modifying // update password from app_user table to this ?2(String password) where id equals to ?1(Long id).
    @Query(value = " UPDATE app_user SET password = ?2 WHERE id = ?1", nativeQuery = true)
    void updateUserPasswordById(Long id, String password);

    @Query(value = "SELECT * FROM app_user ORDER BY id ASC" , nativeQuery = true)
    List<User> findAllUsersOrderByIdAsc();

    @Query(value = "SELECT * FROM book WHERE user_id = ?1", nativeQuery = true)
    List<Book> findAllBorrowedBooksByUserId(Long userId);

    User findUserByUsernameAndPassword(String username, String password);

/*
    void addBorrowedBookById(Long userId, Long bookId);
    void removeBorrowedBookById(Long userId, Long bookId);
*/

}
