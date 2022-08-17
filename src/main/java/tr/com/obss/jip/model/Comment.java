package tr.com.obss.jip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
