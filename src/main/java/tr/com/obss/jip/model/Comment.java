package tr.com.obss.jip.model;

import lombok.*;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNBE
    private String comment;

    @OneToOne
    private User user;

    @OneToOne
    private Book book;
}
