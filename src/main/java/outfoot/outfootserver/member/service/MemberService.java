package outfoot.outfootserver.member.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import outfoot.outfootserver.files.TestFileUploader;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.dto.*;
import outfoot.outfootserver.member.exception.AuthErrorCode;
import outfoot.outfootserver.member.exception.AuthException;
import outfoot.outfootserver.member.repository.MemberRepository;
import outfoot.outfootserver.service.JwtService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final TestFileUploader fileUploader;
    private final String path = "member/";
    private final JwtService jwtService;

    @Transactional // 데이터 변경이 있는 곳에는 Transactional 다시 걸어줘야 함
    public MemberResponse save(SignUpRequest request) {

        // username
        memberRepository.findByUsername(request.getUsername()).ifPresent(e -> {
            throw new AuthException(AuthErrorCode.MEMBER_DUPLICATED);
        });
        // nickname
        memberRepository.findByNickname(request.getNickname()).ifPresent(e -> {
            throw new AuthException(AuthErrorCode.NICKNAME_DUPLICATED);
        });

        String friendCode = createCode();
        Member member = memberRepository.save(SignUpRequest.toMember(request, friendCode));
        return MemberResponse.toMember(member);

    }

    @Transactional
    public MyPageResponse update(MyPageRequest dto, HttpServletRequest request){
        Member member = loadMember(request);
        String originImageUrl = member.getImageUrl();

        String imageUrl = null;
        try {
            if (dto.image() != null && !dto.image().isEmpty()) {
                MultipartFile image = dto.image();
                imageUrl = fileUploader.uploadFile(image, path);

                // 기존 이미지가 존재하고, 새로운 이미지가 존재하는 경우, 기존 이미지 삭제
                if (originImageUrl != null && !originImageUrl.isEmpty()) {
                    fileUploader.deleteFile(originImageUrl, path);
                }
            } else {
            // 새로운 이미지가 존재하지 않는 경우
                imageUrl = originImageUrl;
            }
            member.updateMember(dto, imageUrl);
        } catch (Exception e) {
            throw new AuthException(AuthErrorCode.FILE_NOT_FOUND);
        }

        return MyPageResponse.toMyPage(member, imageUrl);
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

    public Member searchFriend(String searchCode) {
        return memberRepository.findByCode(searchCode)
                .orElseThrow(()-> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));
    }

    // TODO: 로그인 기능 구현 시 리턴 값 수정 필요
    public Member loadMember(Long member_id) {
        return memberRepository.findById(member_id)
                .orElseThrow(() -> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));
    }

    public Member loadMember(HttpServletRequest header) {
        UUID username = UUID.fromString(jwtService.getTokenFromHeader(header));
//        System.out.println(jwtService.getTokenFromHeader(header));
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));
    }

    public MyProfileResponse findMyInfo(HttpServletRequest request) {
        Member member = loadMember(request);
        return MyProfileResponse.builder()
                .name(member.getNickname())
                .myIntro(member.getMyIntro())
                .code(member.getCode())
                .friendCount(member.getFromMember().size())
                .build();
    }
}