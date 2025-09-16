package libraryapp.controller;

import libraryapp.dto.BookDTO;
import libraryapp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public String retrieveAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "admin/book-list";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new BookDTO());
        return "admin/book-add";
    }

    @PostMapping("/add")
    public String addBook(BookDTO bookDTO) { // without @RequestBody can not add data via JSON.
        bookService.addBook(bookDTO);
        return "redirect:/admin";
    }

    @GetMapping("/remove/{bookId}")
    public String removeBook(@PathVariable Long bookId) {
        bookService.removeBook(bookId);
        return "redirect:/admin";
    }

    @GetMapping("/update/{bookId}")
    public String updateBook(@PathVariable("bookId") Long bookId, Model model) {
        model.addAttribute("book", bookService.searchById(bookId));
        return "admin/book-edit";
    }

    @PostMapping("/update/{bookId}") // in order to update in UI side we don't have to use put mapping
    public String updateBook(@PathVariable("bookId") Long bookId, BookDTO bookDTO, Model model) {
        bookService.searchById(bookId);
        model.addAttribute("book", bookService.updateBook(bookId, bookDTO));
        return "redirect:/admin";
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
