package tr.com.obss.jip.dto.create;

import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

@Getter
@Setter
public class CreateNewBook {
    @NotNBE
    private String name;

    @NotNBE
    private String isbn;
}
