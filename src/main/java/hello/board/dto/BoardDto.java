package hello.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class BoardDto {

    private Long id;
    private String title;
    private String content;

    private String userId;
    private String username;

}
