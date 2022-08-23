package tr.com.obss.jip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @NotNBE
    private Date createDate;

    @ManyToOne
    private Genre genre;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Comment> comments;
}
