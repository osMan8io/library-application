package libraryapp.controller;

import libraryapp.dto.UserDTO;
import libraryapp.entity.User;
import libraryapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String login(Model model) {
        model.addAttribute("user", new UserDTO());
        return "login";
    }

/*    @PostMapping()
    public String login(@ModelAttribute("user") UserDTO user, Model model) {
        User loggedInUser = userService.userLogin(user.getUsername(), user.getPassword());

        if (loggedInUser != null) {
            return "redirect:/user/home/" + loggedInUser.getId();
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }

    }*/

}
