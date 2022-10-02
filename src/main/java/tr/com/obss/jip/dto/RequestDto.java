package tr.com.obss.jip.dto;

import lombok.*;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNBE
    private String bookName;

    @NotNBE
    private String bookIsbn;

    @OneToOne
    private AuthorDto author;

    @NotNBE
    private int page;

    @NotNBE
    private String description;

    @NotNBE
    private String photoURL;

    @NotNBE
    private String genreName;

    @NotNBE
    private Date createDate;
}
