package hello.board.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveForm {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    private String username;

    public boolean passwordNotEqualsConfirm() {
        return !password.equals(confirmPassword);
    }
}
