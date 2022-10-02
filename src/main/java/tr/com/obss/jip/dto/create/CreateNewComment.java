package tr.com.obss.jip.dto.create;

import lombok.*;
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