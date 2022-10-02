package tr.com.obss.jip.model;

import lombok.*;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNBE
    private String name;

    @NotNBE
    private String isbn;

    @ManyToOne
    private Author author;

    private int readCounter;

    private int favoriteCounter;

    private double rate;

    private int rateCount;

    private int page;

    private String description;

    private String photoURL;

    @NotNBE
    private Date createDate;

    @ManyToOne
    private Genre genre;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Comment> comments;
}
