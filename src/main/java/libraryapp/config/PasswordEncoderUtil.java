package libraryapp.config;

import libraryapp.entity.User;
import libraryapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Get all users
        var users = userRepository.findAll();

        System.out.println("=== Encoding passwords for existing users ===");

        for (User user : users) {
            String currentPassword = user.getPassword();

            // Check if password is already encoded (BCrypt hashes start with $2a$, $2b$, or $2y$)
            if (!currentPassword.startsWith("$2")) {
                String encodedPassword = passwordEncoder.encode(currentPassword);
                user.setPassword(encodedPassword);
                userRepository.save(user);
                System.out.println("Encoded password for user: " + user.getUsername());
            } else {
                System.out.println("Password already encoded for user: " + user.getUsername());
            }
        }

        System.out.println("=== Password encoding completed ===");

    }
}
