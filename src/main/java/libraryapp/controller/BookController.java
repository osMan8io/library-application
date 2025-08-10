package libraryapp.controller;

import libraryapp.dto.BookDTO;
import libraryapp.service.impl.BookServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class BookController {

    private final BookServiceImpl bookServiceImpl;

    public BookController(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @GetMapping
    public String retrieveAllBooks(Model model) {
        model.addAttribute("books", bookServiceImpl.getAllBooks());
        return "book/book-list";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        model.addAttribute("book", new BookDTO());
        return "book/book-add";
    }

    @PostMapping("/add")
    public String addBook(BookDTO bookDTO) { // without @RequestBody can not add data via JSON.
        bookServiceImpl.addBook(bookDTO);
        return "redirect:/";
    }

    @GetMapping("/remove/{bookId}")
    public String removeBook(@PathVariable Long bookId, Model model) {
        bookServiceImpl.removeBook(bookId);
        return "redirect:/";
    }

    @GetMapping("/update/{bookId}")
    public String updateBook(@PathVariable("bookId") Long bookId, Model model) {
        model.addAttribute("book", bookServiceImpl.searchById(bookId));
        return "book/book-edit";
    }

    @PostMapping("/update/{bookId}") // in order to update in UI side we don't have to use put mapping
    public String updateBook(@PathVariable("bookId") Long bookId, BookDTO bookDTO, Model model) {
        bookServiceImpl.searchById(bookId);
        model.addAttribute("book", bookServiceImpl.updateBook(bookId, bookDTO));
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
