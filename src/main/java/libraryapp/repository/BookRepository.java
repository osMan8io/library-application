package libraryapp.repository;

import libraryapp.entity.Book;
import libraryapp.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByCategory(Category category);
    List<Book> findAllByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT * FROM book ORDER BY id ASC" , nativeQuery = true)
    List<Book> findAllOrderByIdAsc();

}
