package hello.board.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

}
