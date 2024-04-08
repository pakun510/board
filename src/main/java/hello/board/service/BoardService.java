package hello.board.service;

import hello.board.controller.form.BoardSaveForm;
import hello.board.dto.BoardDto;

public interface BoardService {

    BoardDto saveBoard(BoardSaveForm form);

    BoardDto findByBoardId(Long BoardId);

}
