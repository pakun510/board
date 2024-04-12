package hello.board.service;

import hello.board.controller.form.BoardSaveForm;
import hello.board.dto.BoardDto;
import hello.board.entity.Board;
import hello.board.entity.Member;
import hello.board.repository.BoardRepository;
import hello.board.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;
    @Autowired MemberRepository memberRepository;

    @Test
    void saveBoardTest() {
        //given
        Member member = new Member("userid", "1234", "saveTest");
        Long memberId = memberRepository.save(member).getId();
        Board board = boardService.saveBoard(memberId, new BoardSaveForm("TestTitle", "TestContent"));

        //when
        Board findBoard = boardRepository.findById(board.getId()).get();

        //then
        assertThat(findBoard.getId()).isEqualTo(board.getId());
        assertThat(findBoard.getTitle()).isEqualTo(board.getTitle());
        assertThat(findBoard.getContent()).isEqualTo(board.getContent());
    }




}