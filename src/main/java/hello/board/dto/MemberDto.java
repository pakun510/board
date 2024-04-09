package hello.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {

    private Long id;
    private String userId;
    private String username;

}