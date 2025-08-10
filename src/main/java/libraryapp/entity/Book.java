package libraryapp.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import libraryapp.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book extends BaseEntity {

    private String title;
    public String author;
    @Enumerated(EnumType.STRING)
    @JsonIgnore // to ignore direct serialization from enum value
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User borrowedBy;

    @JsonProperty("category") // handles input case sensitivity for Json values
    public void setCategoryFromString(String category) {
        try {
            this.category = Category.valueOf(category.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid category: " + category + ". Valid values are: " + Arrays.toString(Category.values()));
        }
    }

    @JsonGetter("category") // to retrieve again category in all api response
    public String getCategoryAsString() {
        return category != null ? category.name() : null;
    }

}
