package hello.board.service;

import hello.board.controller.form.MemberSaveForm;
import hello.board.entity.Member;
import hello.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final FileService fileService;

    @Override
    public boolean existsMember(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    @Override
    @Transactional
    public void joinMember(MemberSaveForm form) throws IOException {
        memberRepository.save(new Member(form.getUserId(), form.getPassword(), form.getUsername(),
                form.getProfileImage().getOriginalFilename(),
                fileService.saveFileReturnStoreFileName(form.getProfileImage())));
    }

}
