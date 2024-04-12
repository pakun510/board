package hello.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    //TODO 계층형은 아닌 관계 카테고리 구분
//    private Area area;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
