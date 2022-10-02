package tr.com.obss.jip.model;

import lombok.*;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class RespondedBookRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNBE
    private String bookName;

    @NotNBE
    private String bookIsbn;

    @OneToOne
    private Author author;

    @NotNBE
    private Date createDate;

    @Enumerated(EnumType.STRING)
    @NotNBE
    private RespondType respond;
}
