package libraryapp.service.impl;

import libraryapp.dto.BookDTO;
import libraryapp.entity.Book;
import libraryapp.enums.Category;
import libraryapp.mapper.MapperUtil;
import libraryapp.repository.BookRepository;
import libraryapp.service.BookService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LibraryService implements BookService {

    private final BookRepository bookRepository;

    private final MapperUtil mapperUtil;

    public LibraryService(BookRepository bookRepository, MapperUtil mapperUtil) {
        this.bookRepository = bookRepository;
        this.mapperUtil = mapperUtil;
    }


    public Book addBook(BookDTO bookDTO) {
        if (bookDTO.getTitle().trim().isEmpty() || bookDTO.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title or author cannot be empty");
        }
        return bookRepository.save(mapperUtil.convert(bookDTO, Book.class));
    }

    public void removeBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) throw new IllegalArgumentException("Book is not exist");
        bookRepository.deleteById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAllOrderByIdAsc();
        // returns data in ascending order
    }

    public Book updateBook(Long bookId, BookDTO bookDTO) {
        if (!bookRepository.existsById(bookId)) {
            throw new IllegalArgumentException("Book is not exist");
        }
        Book oldBook = mapperUtil.convert(bookDTO, Book.class);
        oldBook.setId(bookId);
        return bookRepository.save(oldBook);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        return bookRepository.findAllByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) throw new IllegalArgumentException("Author cannot be null or empty");
        return bookRepository.findByAuthorContainingIgnoreCase(author.trim());
    }

    @Override
    public Book searchById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> searchByCategory(String category) {
        if (category == null || category.trim().isEmpty()) throw new IllegalArgumentException("Category cannot be null or empty");
        if (!Arrays.stream(Category.values()).anyMatch(value -> value.name().equalsIgnoreCase(category))) throw new IllegalArgumentException("Category does not exist");
        return bookRepository.findByCategory(Category.valueOf(category.toUpperCase()));
    }


}
