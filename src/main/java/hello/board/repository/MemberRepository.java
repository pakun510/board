package hello.board.repository;

import hello.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUserId(String UserId);

    Member findByUserIdAndPassword(String userId, String password);


}
