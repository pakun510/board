package hello.board.repository;

import hello.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    Page<Board> findAllWithMemberAndContainsKeyword(String keyword, Pageable pageable);
}
