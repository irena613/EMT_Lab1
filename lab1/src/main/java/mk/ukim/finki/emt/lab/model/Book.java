package mk.ukim.finki.emt.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.emt.lab.model.enums.Category;

@Data
@Entity
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name ;

    @Enumerated(EnumType.STRING)
    private Category category ;


    @ManyToOne
    private Author author ;

    private Long availableCopies;

    public Book(String name, Category category, Author author, Long availableCopies) {
        this.name = name;
        this.category = category;
        this.availableCopies = availableCopies;
        this.author = author;
    }

}
