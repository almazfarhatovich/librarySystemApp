package appLibrary.db.models;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Integer rating;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @ManyToOne(cascade = {MERGE,DETACH,REFRESH})
    private User owner;

    @ManyToOne(cascade = {MERGE,REFRESH,DETACH})
    private Book book;
}