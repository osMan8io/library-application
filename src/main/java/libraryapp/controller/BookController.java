package libraryapp.controller;

import libraryapp.dto.BookDTO;
import libraryapp.model.ResponseWrapper;
import libraryapp.service.impl.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String retrieveAllBooks(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        return "book/book-list";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new BookDTO());
        return "book/book-add";
    }

    @PostMapping("/add")
    public String addBook(BookDTO bookDTO) { // without @RequestBody can not add data via JSON.
        libraryService.addBook(bookDTO);
        return "redirect:/";
    }

    @GetMapping("/remove/{bookId}")
    public String removeBook(@PathVariable Long bookId, Model model) {
        libraryService.removeBook(bookId);
        return "redirect:/";
    }

    @GetMapping("/update/{bookId}")
    public String updateBook(@PathVariable("bookId") Long bookId, Model model) {
        model.addAttribute("book", libraryService.searchById(bookId));
        return "book/book-edit";
    }

    @PostMapping("/update/{bookId}") // in order to update in UI side we don't have to use put mapping
    public String updateBook(@PathVariable("bookId") Long bookId, BookDTO bookDTO, Model model) {
        libraryService.searchById(bookId);
        model.addAttribute("book", libraryService.updateBook(bookId, bookDTO));
        return "redirect:/";
    }

/*    @GetMapping("/id/{bookId}")
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
    }*/

}
