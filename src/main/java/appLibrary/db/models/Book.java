package appLibrary.db.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photo;

    private String name;

    private String author;

    private String genre;

    private String description;

    private int page;

    private Type typeOfBook;

    private double rating;

    @ManyToOne(cascade = {DETACH,MERGE,PERSIST})
    private User user;

    @OneToMany(cascade = ALL, mappedBy = "book")
    private List<Feedback> feedbacks;
}