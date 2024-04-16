package hello.board.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentSaveRequest {

    @NotNull
    private Long boardId;

    @NotBlank
    private String content;
}
