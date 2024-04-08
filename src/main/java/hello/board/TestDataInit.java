package hello.board;

import hello.board.entity.Board;
import hello.board.repository.BoardRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestDataInit {

    private final BoardRepository boardRepository;

    @PostConstruct
    public void init() {
        for (int i = 0; i < 50; i++) {
            boardRepository.save(new Board("제목"+i, "제목"+i));
        }

    }

}
