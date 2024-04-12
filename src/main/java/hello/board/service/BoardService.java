package hello.board.service;

import hello.board.controller.form.BoardSaveForm;
import hello.board.entity.Board;

public interface BoardService {

    Board saveBoard(Long memberId, BoardSaveForm form);

    void editBoard(Long boardId, BoardSaveForm form);

}
