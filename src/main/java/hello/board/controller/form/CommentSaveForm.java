package hello.board.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentSaveForm {

    @NotBlank
    private String content;
}
