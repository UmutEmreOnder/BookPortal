package tr.com.obss.jip.dto.create;

import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.Book;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CreateNewAuthor {
    @NotNBE
    private String name;

    @NotNBE
    private String surname;

    @NotNBE
    private String username;

    @NotNBE
    @Size(min = 3, max = 20)
    private String password;
}
