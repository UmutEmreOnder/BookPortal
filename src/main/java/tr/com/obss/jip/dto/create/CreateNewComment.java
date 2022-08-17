package tr.com.obss.jip.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewComment {
    @NotNBE
    private String comment;
}