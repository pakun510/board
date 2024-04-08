package hello.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String password;
    private String username;


    public static Member joinMember(String userId, String password, String username) {
        return new Member(userId, password, username);
    }

    private Member(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }
}
