package libraryapp.repository;

import libraryapp.entity.Book;
import libraryapp.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorIgnoreCase(String author);
    List<Book> findByCategory(Category category);
    List<Book> findAllByTitleIgnoreCase(String title);

    @Query("SELECT b FROM Book b " +
            "WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            " OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) " +
            " OR LOWER(b.category) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> findByTitleOrAuthorOrCategory(@Param("query") String query);

    @Query(value = "SELECT * FROM book ORDER BY id ASC" , nativeQuery = true)
    List<Book> findAllOrderByIdAsc();

}
