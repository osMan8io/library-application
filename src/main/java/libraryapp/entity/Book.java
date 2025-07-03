package libraryapp.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import libraryapp.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private int id;
    private String title;
    private String author;
    @Enumerated(EnumType.STRING)
    @JsonIgnore // to ignore direct serialization from enum value
    private Category category;

    @JsonProperty("category") // handles input case sensitivity for Json values
    public void setCategoryFromString(String category) {
        this.category = Category.valueOf(category.trim().toUpperCase());
    }

    @JsonGetter("category") // to retrieve again category in all api response
    public String getCategoryAsString() {
        return category.name();
    }

}
