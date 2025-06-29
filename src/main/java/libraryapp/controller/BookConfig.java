package libraryapp.controller;

import libraryapp.entity.Book;
import libraryapp.enums.Category;
import libraryapp.service.impl.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookConfig {

    private final LibraryService libraryService;

    public BookConfig(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<Book> retrieveAllBooks() {
        return libraryService.getAllBooks();
    }

    @PostMapping
    public void addBook(@RequestBody Book book) { // without @RequestBody can not add data via JSON.
        libraryService.addBook(book);
    }

    @DeleteMapping("/remove/{bookId}")
    public Book removeBook(@PathVariable("bookId") int bookId) {
        return libraryService.removeBook(bookId);
    }

    @PutMapping("/update/{bookId}")
    public Book updateBook(@PathVariable("bookId") int bookId,@RequestBody Book book) {
       return libraryService.updateBook(bookId,book);
    }

    @GetMapping("/id/{bookId}")
    public Book getBookById(@PathVariable("bookId") int bookId) {
        return libraryService.searchById(bookId);
    }

    @GetMapping("/title/{title}")
    public Book getBookByTitle(@PathVariable("title") String title) {
        return libraryService.searchByTitle(title);
    }

    @GetMapping("/author/{author}")
    public Book getBookByAuthor(@PathVariable("author") String author) {
        return libraryService.searchByAuthor(author);
    }

    @GetMapping("/category/{category}")
    public List<Book> getBookByCategory(@PathVariable("category") String category) {
        return libraryService.searchByCategory(category);
    }

}
