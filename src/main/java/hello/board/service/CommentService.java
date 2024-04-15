package hello.board.service;

import hello.board.controller.form.CommentSaveForm;

public interface CommentService {


    void saveComment(Long memberId, Long boardId, CommentSaveForm form);

}
