package tr.com.obss.jip.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;

    private String comment;

    private UserSummaryDto user;
}
