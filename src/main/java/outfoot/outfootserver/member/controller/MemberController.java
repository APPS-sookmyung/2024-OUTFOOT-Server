package outfoot.outfootserver.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.member.dto.MemberResponse;
import outfoot.outfootserver.member.dto.MyPageRequest;
import outfoot.outfootserver.member.dto.MyPageResponse;
import outfoot.outfootserver.member.dto.SignUpRequest;
import outfoot.outfootserver.member.service.MemberService;

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

    @PostMapping("/myPage/{member_id}")
    @Operation(summary = "프로필 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 정보 수정에 성공하였습니다."),
    })
    public BasicResponse<MyPageResponse> UpdateMyPage (@Valid @ModelAttribute MyPageRequest dto, @PathVariable(name = "member_id") Long memberId) {
        MyPageResponse member = memberService.update(dto, memberId);
        return ResponseUtil.success(member);
    }

    private final MemberService memberService;
}
