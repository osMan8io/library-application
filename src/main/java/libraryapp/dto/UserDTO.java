package libraryapp.dto;

import jakarta.validation.constraints.NotBlank;
import libraryapp.entity.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String address;

    private List<Book> borrowedBooks;

}
