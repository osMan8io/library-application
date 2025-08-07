package libraryapp.controller;

import libraryapp.dto.UserDTO;
import libraryapp.entity.Book;
import libraryapp.entity.User;
import libraryapp.service.impl.BookServiceImpl;
import libraryapp.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    private final BookServiceImpl bookServiceImpl;


    public UserController(UserServiceImpl userServiceImpl, BookServiceImpl bookServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.bookServiceImpl = bookServiceImpl;
    }

/*    @GetMapping
    public String retrieveAllUsers(Model model) {
        model.addAttribute("users", userServiceImpl.readAllUsers());
        return "user/library";
    }*/

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserDTO user, Model model) {
        User loggedInUser = userServiceImpl.userLogin(user.getUsername(), user.getPassword());

        if (loggedInUser != null) {
            return "redirect:/user/home/" + loggedInUser.getId();
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "user/login";
        }

    }

    @GetMapping("/home/{userId}")
    public String retrieveHomePage(@PathVariable Long userId, Model model) {
        User user = userServiceImpl.readUserById(userId);
        System.out.println("USER:" + user );
        model.addAttribute("books", bookServiceImpl.getAllBooks());
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
        userServiceImpl.addUser(userDTO);
        return "redirect:/user/login";
    }

/*    @GetMapping("/remove/{userId}")
    public String deleteUserById(@PathVariable Long userId) {
        userServiceImpl.deleteUserById(userId);
        return "redirect:/user";
    }*/

    @GetMapping("/update/{userId}")
    public String updatePasswordById(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userServiceImpl.readUserById(userId));
        return "user/update";
    }

    @PostMapping("/update/{userId}")
    public String updateUserPasswordById(@PathVariable Long userId, String password) {
        userServiceImpl.updateUserPasswordById(userId, password);
        return "redirect:/user";
    }

    @GetMapping("/addBook/{userId}")
    public String showBookList(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userServiceImpl.readUserById(userId));
        model.addAttribute("books", bookServiceImpl.getAllBooks());
        return "user/library"; // if we use the same HTML file across multiple endpoints, every endpoint must provide both books and user in the model
    }

    @GetMapping("/{userId}/borrow/{bookId}")
    public String borrowBook(@PathVariable Long userId, @PathVariable Long bookId) {
        userServiceImpl.borrowBook(userId, bookId);
        return "redirect:/user/addBook/" + userId;
    }

    @GetMapping("/return/{userId}/{bookId}")
    public String returnBook(@PathVariable Long userId,@PathVariable Long bookId, Model model) {
        Book book = bookServiceImpl.searchById(bookId);

        if (book.getBorrowedBy() == null) {
            model.addAttribute("error", "This book is not borrowed by this user");
            return "redirect:/user/home/" + userId;
        }
        userServiceImpl.returnBook(bookId);
        return "redirect:/user/home/" + userId;
    }


}
