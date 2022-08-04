package tr.com.obss.jip.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 3, max = 20)
    @NotNBE
    private String name;

    @NotNBE
    private String surname;

    @NotNBE
    @PositiveOrZero
    @Max(120)
    private Integer age;

    @NotNBE
    @Column(unique = true, nullable = false)
    private String username;

    @NotNBE
    private String password;

    @ManyToMany
    private List<Role> roles;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Book> readList;


    @OneToMany(cascade = {CascadeType.ALL})
    private List<Book> favoriteList;
}
