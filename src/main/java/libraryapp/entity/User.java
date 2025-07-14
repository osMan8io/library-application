package libraryapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//tells Jackson to ignore the lazy-loading handler fields during serialization.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "app_user") // Renamed to avoid conflict
public class User extends BaseEntity {

    private String username;
    private String password;
    @OneToMany
    private List<Book> borrowedBooks;
}
