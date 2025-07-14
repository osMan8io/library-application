package libraryapp.controller;

import libraryapp.dto.BookDTO;
import libraryapp.entity.Book;
import libraryapp.model.ResponseWrapper;
import libraryapp.service.impl.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
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
    public ResponseEntity<ResponseWrapper> addBook(@RequestBody BookDTO bookDTO) { // without @RequestBody can not add data via JSON.
                return ResponseEntity.ok(ResponseWrapper.builder()
                        .code(200)
                        .success(true)
                        .message("Book is successfully added")
                        .data(libraryService.addBook(bookDTO))
                        .build());

    }

    @DeleteMapping("/remove/{bookId}")
    public ResponseEntity<ResponseWrapper> removeBook(@PathVariable("bookId") Long bookId) {

            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Book is successfully deleted")
                    .data(libraryService.removeBook(bookId))
                    .build());
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseWrapper> updateBook(@PathVariable("bookId") Long bookId,@RequestBody BookDTO book) {
            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Book is successfully updated")
                    .data(libraryService.updateBook(bookId,book))
                    .build());

    }

    @GetMapping("/id/{bookId}")
    public ResponseEntity<ResponseWrapper> getBookById(@PathVariable("bookId") Long bookId) {
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
                    .data(libraryService.searchByAuthor(author.trim().toLowerCase()))
                    .build());

    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ResponseWrapper> getBookByCategory(@PathVariable("category") String category) {
            return ResponseEntity.ok(ResponseWrapper.builder()
                    .code(200)
                    .success(true)
                    .message("Books are successfully retrieved")
                    .data(libraryService.searchByCategory(category))
                    .build());
    }

}
