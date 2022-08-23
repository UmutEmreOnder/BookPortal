package tr.com.obss.jip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.Role;

import javax.persistence.Column;
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

public class UserDto extends UserSummaryDto {
    @NotNBE
    @PositiveOrZero
    @Max(120)
    private Integer age;

    @NotNBE
    @Email
    @Column(unique = true)
    private String email;

    @NotNBE
    private Date createDate;

    @ManyToMany
    private List<Role> roles;
}
