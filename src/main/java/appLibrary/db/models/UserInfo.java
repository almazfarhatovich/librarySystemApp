package appLibrary.db.models;

import appLibrary.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "userInfo")
@NoArgsConstructor
@Getter
@Setter
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String country;

    private int countOfBooks;

    @Column(name = "about_me")
    private String aboutMe;

    @OneToOne(cascade = {MERGE,REFRESH,DETACH})
    private User user;

}
