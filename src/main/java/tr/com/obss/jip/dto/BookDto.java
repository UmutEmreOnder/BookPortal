package tr.com.obss.jip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.Comment;
import tr.com.obss.jip.model.Genre;

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

    private int rate;

    private int rateCount;

    @OneToMany
    private List<CommentDto> comments;

    @ManyToOne
    private AuthorDto author;

    private Genre genre;

    @NotNBE
    private Date createDate;
}

