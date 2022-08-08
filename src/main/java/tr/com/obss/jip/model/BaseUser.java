package tr.com.obss.jip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNBE
    private String name;

    @NotNBE
    private String surname;

    @NotNBE
    @PositiveOrZero
    @Max(120)
    private Integer age;

    @Email
    @Column(unique = true)
    @NotNBE
    private String email;

    @NotNBE
    @Column(unique = true, nullable = false)
    private String username;

    @NotNBE
    private String password;

    @NotNBE
    private Date createDate;

    @ManyToMany
    private List<Role> roles;
}
