package tr.com.obss.jip.dto.create;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateNewUser {

    @NotNBE
    @Length(min = 3, max = 20)
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
    private String username;

    @Size(min = 3, max = 20)
    private String password;
}
