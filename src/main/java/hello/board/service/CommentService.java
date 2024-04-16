package hello.board.service;

import hello.board.controller.form.CommentSaveRequest;

public interface CommentService {


    void saveComment(Long memberId, CommentSaveRequest form);

}
