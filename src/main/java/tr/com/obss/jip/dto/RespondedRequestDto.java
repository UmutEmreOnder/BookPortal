package tr.com.obss.jip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.RespondType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondedRequestDto {
    @NotNBE
    private String bookName;

    @NotNBE
    private String bookIsbn;

    @Enumerated(EnumType.STRING)
    @NotNBE
    private RespondType respond;
}
