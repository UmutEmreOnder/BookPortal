package tr.com.obss.jip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.Role;

import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto extends AuthorSummaryDto {
    @NotNBE
    private String email;

    @NotNBE
    private String username;

    @ManyToMany
    private List<Role> roles;

    @NotNBE
    private Date createDate;
}
