package tr.com.obss.jip.dto.create;

import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.OneToOne;

@Getter
@Setter
public class CreateNewRequest {
    @NotNBE
    private String bookName;

    @NotNBE
    private String bookIsbn;

    @OneToOne
    private Long authorId;
}
