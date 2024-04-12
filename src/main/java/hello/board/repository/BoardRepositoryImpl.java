package hello.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.board.entity.Board;
import hello.board.entity.QBoard;
import hello.board.entity.QMember;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static hello.board.entity.QBoard.*;
import static hello.board.entity.QMember.*;

public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> findAllWithMemberAndContainsKeyword(String keyword, Pageable pageable) {

        List<Board> boardList = queryFactory.selectFrom(board)
                .leftJoin(board.member, member).fetchJoin()
                .where(titleContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.id.desc())
                .fetch();

        Long total = queryFactory.select(board.count())
                .from(board)
                .where(titleContains(keyword))
                .fetchOne();

        return new PageImpl<>(boardList, pageable, total);
    }
    private BooleanExpression titleContains(String keyword) {
        return StringUtils.hasText(keyword) ? board.title.contains(keyword) : null;
    }

    //TODO 제목 또는 내용 검색 수정필요. .or 시 문제점 찾아서 고쳐야함.
    private BooleanExpression contentContains(String keyword) {
        return StringUtils.hasText(keyword) ? board.content.contains(keyword) : null;
    }
}
