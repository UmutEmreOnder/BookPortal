package tr.com.obss.jip.dto;

import lombok.*;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.Genre;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNBE
    private String name;

    @NotNBE
    private String isbn;

    private int readCounter;

    private int favoriteCounter;

    private double rate;

    private int rateCount;

    @NotNBE
    private int page;

    @NotNBE
    private String description;

    @NotNBE
    private String photoURL;

    @ManyToOne
    private AuthorSummaryDto author;

    private Genre genre;

    @NotNBE
    private Date createDate;
}

