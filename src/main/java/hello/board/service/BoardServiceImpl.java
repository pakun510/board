package hello.board.service;

import hello.board.controller.form.BoardSaveForm;
import hello.board.entity.Board;
import hello.board.entity.Member;
import hello.board.repository.BoardRepository;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Board saveBoard(Long memberId, BoardSaveForm form) {

        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        Board board = new Board(form.getTitle(), form.getContent());
        member.writeBoard(board);

        return boardRepository.save(board);
    }

    @Override
    @Transactional
    public void editBoard(Long boardId, BoardSaveForm form) {

        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        board.edit(form.getTitle(), form.getContent());

    }


}
