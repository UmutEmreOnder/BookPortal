package tr.com.obss.jip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import tr.com.obss.jip.annotations.NotNBE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentDto {
    @NotNBE
    @Length(min = 3, max = 20)
    private String name;

    @NotNBE
    private String surname;
}
