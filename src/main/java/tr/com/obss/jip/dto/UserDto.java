package tr.com.obss.jip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.Book;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNBE
    @Length(min = 3, max = 20)
    private String name;

    @NotNBE
    private String surname;

    @NotNBE
    @PositiveOrZero
    @Max(120)
    private Integer age;

    @NotNBE
    private String username;

    @OneToMany
    private List<Book> readList;

    @OneToMany
    private List<Book> favoriteList;
}
