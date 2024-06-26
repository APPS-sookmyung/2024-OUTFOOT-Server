package outfoot.outfootserver.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.dto.SignUpRequest;
import outfoot.outfootserver.member.exception.AuthErrorCode;
import outfoot.outfootserver.member.exception.AuthException;
import outfoot.outfootserver.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional // 데이터 변경이 있는 곳에는 Transactional 다시 걸어줘야 함
    public long save(SignUpRequest request) {
        memberRepository.findByUsername(request.getUsername()).ifPresent(e -> {
            throw new AuthException(AuthErrorCode.MEMBER_DUPLICATED);
        });
        Member member = memberRepository.save(SignUpRequest.toMember(request));
        return member.getId();
    }
}
