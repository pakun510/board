package hello.board.service;

import hello.board.controller.form.BoardSaveForm;
import hello.board.dto.BoardDto;
import hello.board.entity.Board;
import hello.board.repository.BoardRepository;
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

    @Test
    void saveBoardTest() {
        //given
        BoardDto boardDto = boardService.saveBoard(new BoardSaveForm("TestTitle", "TestContent"));

        //when
        Board findBoard = boardRepository.findById(boardDto.getId()).get();

        //then
        assertThat(findBoard.getId()).isEqualTo(boardDto.getId());
        assertThat(findBoard.getTitle()).isEqualTo(boardDto.getTitle());
        assertThat(findBoard.getContent()).isEqualTo(boardDto.getContent());
    }




}