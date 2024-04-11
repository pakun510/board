package hello.board;

import hello.board.entity.Board;
import hello.board.entity.Member;
import hello.board.repository.BoardRepository;
import hello.board.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestDataInit {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 200; i++) {
            boardRepository.save(new Board("제목"+i, "내용"+i));
        }
        Member member = memberRepository.save(new Member("test", "1234", "testUser"));


    }

}
