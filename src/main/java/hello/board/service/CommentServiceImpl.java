package hello.board.service;

import hello.board.controller.form.CommentSaveForm;
import hello.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;


    @Override
    public void saveComment(Long memberId, Long boardId, CommentSaveForm form) {

    }
}
