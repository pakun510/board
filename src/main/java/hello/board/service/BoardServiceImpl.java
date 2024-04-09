package hello.board.service;

import hello.board.controller.form.BoardSaveForm;
import hello.board.dto.BoardDto;
import hello.board.entity.Board;
import hello.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    @Override
    @Transactional
    public BoardDto saveBoard(BoardSaveForm form) {
        Board savedBoard = boardRepository.save(new Board(form.getTitle(), form.getContent()));

        return BoardDto.builder()
                .id(savedBoard.getId())
                .title(savedBoard.getTitle())
                .content(savedBoard.getContent())
                .build();
    }

    @Override
    public BoardDto findByBoardId(Long BoardId) {
        Board findBoard = boardRepository.findById(BoardId).orElseThrow(IllegalArgumentException::new);

        return BoardDto.builder()
                .id(findBoard.getId())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .build();

    }



}
