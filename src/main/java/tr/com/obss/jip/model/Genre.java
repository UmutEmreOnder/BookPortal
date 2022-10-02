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
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    @NotNBE
    private GenreType name;
}
