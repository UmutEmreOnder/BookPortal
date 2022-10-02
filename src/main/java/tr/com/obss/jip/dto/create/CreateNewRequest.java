package tr.com.obss.jip.dto.create;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tr.com.obss.jip.annotations.NotNBE;

@Getter
@Setter
@ToString
public class CreateNewRequest {
    @NotNBE
    private String bookName;

    @NotNBE
    private String bookIsbn;

    private String genreName;

    @NotNBE
    private int page;

    @NotNBE
    private String description;

    @NotNBE
    private String photoURL;
}
