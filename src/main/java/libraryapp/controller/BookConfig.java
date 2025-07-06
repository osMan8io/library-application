package libraryapp.controller;

import libraryapp.entity.Book;
import libraryapp.enums.Category;
import libraryapp.model.ResponseWrapper;
import libraryapp.service.impl.LibraryService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseWrapper> retrieveAllBooks() {
        return ResponseEntity.ok(ResponseWrapper.builder()
                .code(200)
                .success(true)
                .message("All books are retrieved")
                .data(libraryService.getAllBooks())
                .build());
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> addBook(@RequestBody Book book) { // without @RequestBody can not add data via JSON.
            try {
                return ResponseEntity.ok(ResponseWrapper.builder()
                        .code(200)
                        .success(true)
                        .message("Book is successfully added")
                        .data(libraryService.addBook(book))
                        .build());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(ResponseWrapper.builder()
                        .code(400)
                        .success(false)
                        .message("Title/Author can not be empty")
                        .build());
            }
    }

    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<ResponseWrapper> removeBook(@PathVariable("bookId") int bookId) {
        try {
            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Book is successfully deleted")
                    .data(libraryService.removeBook(bookId))
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseWrapper.builder()
                    .code(400)
                    .success(false)
                    .message("Invalid book id")
                    .build());
        }
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseWrapper> updateBook(@PathVariable("bookId") int bookId,@RequestBody Book book) {
            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Book is successfully updated")
                    .data(libraryService.updateBook(bookId,book))
                    .build());

    }

    @GetMapping("/id/{bookId}")
    public ResponseEntity<ResponseWrapper> getBookById(@PathVariable("bookId") int bookId) {
            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Book is successfully retrieved")
                    .data(libraryService.searchById(bookId))
                    .build());

    }

    @GetMapping("/title/{title}")
    public ResponseEntity<ResponseWrapper> getBookByTitle(@PathVariable("title") String title) {
            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Book is successfully retrieved")
                    .data(libraryService.searchByTitle(title))
                    .build());

    }

    @GetMapping("/author/{author}")
    public ResponseEntity<ResponseWrapper> getBookByAuthor(@PathVariable("author") String author) {
            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Book is successfully retrieved")
                    .data(libraryService.searchByAuthor(author))
                    .build());

    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ResponseWrapper> getBookByCategory(@PathVariable("category") String category) {
        try {
            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Books are successfully retrieved")
                    .data(libraryService.searchByCategory(category))
                    .build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseWrapper.builder()
                    .code(400)
                    .success(false)
                    .message("Invalid category")
                    .build());
        }
    }

}
