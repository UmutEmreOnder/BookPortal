package tr.com.obss.jip.dto.create;

import lombok.*;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.Author;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNewBook {
    @NotNBE
    private String name;

    @NotNBE
    private String isbn;

    private Author author;

    private String genreName;

    @NotNBE
    private int page;

    @NotNBE
    private String description;

    @NotNBE
    private String photoURL;
}
