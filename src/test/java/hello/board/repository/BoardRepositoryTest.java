package hello.board.repository;

import hello.board.entity.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired BoardRepository boardRepository;

    @Test
    void findByIdJoinMemberFetchTest() {
        Board board = boardRepository.findByIdJoinFetchMember(1L).get();
        Assertions.assertThat(board.getMember().getUsername()).isEqualTo("testUser");

    }
}