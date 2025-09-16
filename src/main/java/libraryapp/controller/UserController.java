package libraryapp.controller;

import libraryapp.dto.UserDTO;
import libraryapp.entity.Book;
import libraryapp.entity.User;
import libraryapp.service.BookService;
import libraryapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final BookService bookService;

    private final PasswordEncoder passwordEncoder;


    public UserController(UserService userService, BookService bookService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.bookService = bookService;
        this.passwordEncoder = passwordEncoder;
    }

/*    @GetMapping
    public String retrieveAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.readAllUsers());
        return "user/library";
    }*/

/*    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserDTO user, Model model) {
        User loggedInUser = userService.userLogin(user.getUsername(), user.getPassword());

        if (loggedInUser != null) {
            return "redirect:/user/home/" + loggedInUser.getId();
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

    }*/

    @GetMapping("/home/{userId}")
    public String retrieveHomePage(@PathVariable Long userId, Model model) {
        User user = userService.readUserById(userId);
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("user", user);
        return "user/library";
    }

    @GetMapping("/add")
    public String addNewUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user/add";
    }

    @PostMapping("/add")
    public String addNewUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.addUser(userDTO);
        return "redirect:/user/login";
    }

/*    @GetMapping("/remove/{userId}")
    public String deleteUserById(@PathVariable Long userId) {
        userServiceImpl.deleteUserById(userId);
        return "redirect:/user";
    }*/

    @GetMapping("/update/{userId}")
    public String updatePasswordById(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.readUserById(userId));
        return "user/update";
    }

    @PostMapping("/update/{userId}")
    public String updateUserPasswordById(@PathVariable Long userId, String password) {
        userService.updateUserPasswordById(userId, password);
        return "redirect:/user";
    }

    @GetMapping("/addBook/{userId}")
    public String showBookList(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.readUserById(userId));
        model.addAttribute("books", bookService.getAllBooks());
        return "user/library"; // if we use the same HTML file across multiple endpoints, every endpoint must provide both books and user in the model
    }

    @GetMapping("/{userId}/borrow/{bookId}")
    public String borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        userService.borrowBook(userId, bookId);
        return "redirect:/user/addBook/" + userId;
    }

    @GetMapping("/return/{userId}/{bookId}")
    public String returnBook(@PathVariable Long userId,@PathVariable Long bookId, Model model) {
        Book book = bookService.searchById(bookId);

        if (book.getBorrowedBy() == null) {
            model.addAttribute("error", "This book is not borrowed by this user");
            return "redirect:/user/home/" + userId;
        }
        userService.returnBook(bookId);
        return "redirect:/user/home/" + userId;
    }

    @GetMapping("/search") // With @RequestParam, we use input name form HTML tag
    public String searchBook(@RequestParam("query") String query, Model model) {
        model.addAttribute("books", bookService.search(query));
        model.addAttribute("user", bookService.search(query));
        return "user/library";
    }

}
