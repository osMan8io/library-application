package libraryapp.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import libraryapp.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer, handler"})
public class BookDTO {

/* if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
   throw new IllegalArgumentException("Title must not be blank");
 }*/
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Category category;

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
