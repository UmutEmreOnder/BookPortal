package tr.com.obss.jip.dto.create;

import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

@Getter
@Setter
public class CreateNewRequest {
    @NotNBE
    private String bookName;

    @NotNBE
    private String bookIsbn;

    private Long authorId;
}
