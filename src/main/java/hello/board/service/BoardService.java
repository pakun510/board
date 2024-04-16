package hello.board.service;

import hello.board.controller.form.BoardSaveForm;
import hello.board.entity.Board;

import java.io.IOException;

public interface BoardService {

    Board saveBoard(Long memberId, BoardSaveForm form) throws IOException;

    void editBoard(Long boardId, BoardSaveForm form);

}
