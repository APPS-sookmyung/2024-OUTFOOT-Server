package outfoot.outfootserver.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.dto.MemberResponse;
import outfoot.outfootserver.member.dto.SignUpRequest;
import outfoot.outfootserver.member.exception.AuthErrorCode;
import outfoot.outfootserver.member.exception.AuthException;
import outfoot.outfootserver.member.repository.MemberRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional // 데이터 변경이 있는 곳에는 Transactional 다시 걸어줘야 함
    public MemberResponse save(SignUpRequest request) {
        memberRepository.findByUsername(request.getUsername()).ifPresent(e -> {
            throw new AuthException(AuthErrorCode.MEMBER_DUPLICATED);
        });

        String friendCode = createCode();
        Member member = memberRepository.save(SignUpRequest.toMember(request, friendCode));
        return MemberResponse.toMember(member);
    }

    // 멤버의 친구 코드 uuid 생성
    public String createCode() {
        String friendCode = UUID.randomUUID().toString();
        byte[] friendCodeBytes = friendCode.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            hashBytes = messageDigest.digest(friendCodeBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new AuthException(AuthErrorCode.UUID_CREATE_ERROR);
        }

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            sb.append(String.format("%02x", hashBytes[j]));
        }
        return sb.toString();
    }

    // TODO: 로그인 기능 구현 시 리턴 값 수정 필요
    public Member loadMember(Long member_id) {
        return memberRepository.findById(member_id)
                .orElseThrow(() -> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));
    }
}