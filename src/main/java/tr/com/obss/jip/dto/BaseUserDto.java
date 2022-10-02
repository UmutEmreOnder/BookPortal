package tr.com.obss.jip.dto;

import lombok.*;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BaseUserDto {
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
    private Date createDate;

    @ManyToMany
    private List<Role> roles;
}
