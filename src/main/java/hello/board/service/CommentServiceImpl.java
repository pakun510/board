package hello.board.service;

import hello.board.controller.form.CommentSaveRequest;
import hello.board.entity.Board;
import hello.board.entity.Comment;
import hello.board.entity.Member;
import hello.board.repository.BoardRepository;
import hello.board.repository.CommentRepository;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void saveComment(Long memberId, CommentSaveRequest form) {

        Member findMember = memberRepository.findById(memberId).get();
        Board findBoard = boardRepository.findById(form.getBoardId()).get();

        Comment comment = new Comment(form.getContent());

        findMember.writeComment(comment);
        findBoard.addComment(comment);

        commentRepository.save(comment);

    }
}
