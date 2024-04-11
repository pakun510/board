package hello.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSessionDto {
    private Long id;
    private String username;
}
