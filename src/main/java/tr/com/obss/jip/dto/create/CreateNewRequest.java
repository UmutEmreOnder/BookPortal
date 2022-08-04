package tr.com.obss.jip.dto.create;

import tr.com.obss.jip.annotations.NotNBE;

import javax.persistence.OneToOne;

public class CreateNewRequest {
    @NotNBE
    private String bookName;

    @NotNBE
    private String bookIsbn;

    @OneToOne
    private Long authorId;
}
