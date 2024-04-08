package hello.board.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveForm {

    @NotEmpty
    @Length(min = 2, max = 20)
    private String title;

    @NotEmpty
    private String content;

}
