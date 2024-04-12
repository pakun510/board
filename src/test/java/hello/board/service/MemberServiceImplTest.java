package hello.board.service;

import hello.board.controller.form.MemberSaveForm;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void memberValidationByUserId() {
        //given
        Member member1 = new Member("test", "1234", "testUserA");
        memberRepository.save(member1);

        //when
        MemberSaveForm member2 = MemberSaveForm.builder()
                .userId("test")
                .username("testUserB")
                .password("1234")
                .confirmPassword("1234")
                .build();
        boolean existsMember = memberService.existsMember(member2.getUserId());

        //then
        assertThat(existsMember).isTrue();

    }
}