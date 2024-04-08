package hello.board.controller.form;

import lombok.Data;

@Data
public class MemberSaveForm {

    private String userId;
    private String password;
    private String confirmPassword;
    private String username;

    public boolean validationPassword() {
        return !password.equals(confirmPassword);
    }
}
