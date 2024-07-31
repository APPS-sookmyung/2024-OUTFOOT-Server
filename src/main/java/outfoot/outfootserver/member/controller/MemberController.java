package outfoot.outfootserver.member.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.dto.MemberResponse;
import outfoot.outfootserver.member.dto.SignUpRequest;
import outfoot.outfootserver.member.service.MemberService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입", description = "Member API")
public class MemberController {

    @PostMapping("/signup")
    @Parameters({
            @Parameter(name = "username", description = "공백 X", example = "ajeong7038"),
            @Parameter(name = "nickname", description = "닉네임", example = "ajeong"),
            @Parameter(name = "password", description = "공백 X", example = "password123"),
            @Parameter(name = "email", description = "공백 X", example = "ajung7038@naver.com"),
            @Parameter(name = "code", description = "공백 X", example = "ABC98273"),
    })
    public BasicResponse<MemberResponse> SignUp (@Valid@RequestBody SignUpRequest dto) {
        MemberResponse member = memberService.save(dto);
        return ResponseUtil.success(member);
    }

    private final MemberService memberService;
    // 내 정보 확인
    // BasicResponse로 감싸주기
}
