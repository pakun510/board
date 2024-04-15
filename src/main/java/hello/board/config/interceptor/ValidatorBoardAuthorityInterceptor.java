package hello.board.config.interceptor;

import hello.board.config.SessionUtils;
import hello.board.dto.MemberSessionDto;
import hello.board.entity.Board;
import hello.board.repository.BoardRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ValidatorBoardAuthorityInterceptor implements HandlerInterceptor {

    private final BoardRepository boardRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("게시글 권한 인터셉터 실행 {}", requestURI);

        Long boardId = Long.valueOf(requestURI.split("/")[2]);
        log.info("게시글 번호 = {}", boardId);

        Optional<Board> findBoardOptional = boardRepository.findByIdJoinFetchMember(boardId);
        if (findBoardOptional.isEmpty()) {
            log.info("해당 BoardId의 결과가 없음 = {}", boardId);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }

        MemberSessionDto memberSessionDto = SessionUtils.getMemberSessionDto(request);
        Board findBoard = findBoardOptional.get();

        if (!memberSessionDto.getId().equals(findBoard.getMember().getId())) {
            log.info("해당 BoardId의 권한 없음 = {}", boardId);
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }


}
