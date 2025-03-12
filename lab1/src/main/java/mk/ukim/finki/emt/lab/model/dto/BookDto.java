package mk.ukim.finki.emt.lab.model.dto;

import lombok.Data;
import mk.ukim.finki.emt.lab.model.enums.Category;

@Data
public class BookDto {

    private String name ;

    private Category category ;

    private Long author ;

    private Long availableCopies;

    public BookDto(String name, Category category, Long author, Long availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }
}
