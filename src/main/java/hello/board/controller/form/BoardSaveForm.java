package hello.board.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardSaveForm {

    @NotBlank
    @Length(min = 2, max = 20)
    private String title;

    @NotBlank
    private String content;

}
