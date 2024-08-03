package outfoot.outfootserver.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 회원입니다."),
    })
    public BasicResponse<MemberResponse> SignUp (@Valid@RequestBody SignUpRequest dto) {
        MemberResponse member = memberService.save(dto);
        return ResponseUtil.success(member);
    }

    private final MemberService memberService;
    // 내 정보 확인
    // BasicResponse로 감싸주기
}
