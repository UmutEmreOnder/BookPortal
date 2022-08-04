package tr.com.obss.jip.dto;

import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

@Getter
@Setter
public class CreateNewBookRequest {
    @NotNBE
    private String name;

    @NotNBE
    private String isbn;
}
