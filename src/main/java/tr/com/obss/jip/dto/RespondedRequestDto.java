package tr.com.obss.jip.dto;

import lombok.*;
import tr.com.obss.jip.annotations.NotNBE;
import tr.com.obss.jip.model.RespondType;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondedRequestDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNBE
    private String bookName;

    @NotNBE
    private String bookIsbn;

    @Enumerated(EnumType.STRING)
    @NotNBE
    private RespondType respond;

    @NotNBE
    private Date createDate;
}
