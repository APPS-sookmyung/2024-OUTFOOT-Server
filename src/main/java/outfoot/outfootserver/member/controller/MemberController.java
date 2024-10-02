package outfoot.outfootserver.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.dto.*;
import outfoot.outfootserver.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입", description = "Member API")
public class MemberController {

    private final MemberService memberService;

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

    @PutMapping("/my")
    @Operation(summary = "프로필 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 정보 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
    })
    public BasicResponse<MyPageResponse> UpdateMyPage (@Valid @ModelAttribute MyPageRequest dto, HttpServletRequest request) {
        MyPageResponse member = memberService.update(dto, request);
        return ResponseUtil.success(member);
    }

    @PutMapping("/my/images")
    @Operation(summary = "프로필 내용 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 정보 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
    })
    public BasicResponse<MyPageResponse> UpdateMyPageWithImage (@Valid @ModelAttribute MyPageImageRequest dto, HttpServletRequest request) {
        MyPageResponse member = memberService.updateImage(dto, request);
        return ResponseUtil.success(member);
    }

    @GetMapping("/my")
    @Operation(summary = "내 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내 정보 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
    })
    public BasicResponse<MyProfileResponse> MyProfile (HttpServletRequest request) {
        return ResponseUtil.success(memberService.findMyInfo(request));
    }

    @GetMapping("/test/test")
    public BasicResponse<Member> testTest (HttpServletRequest request) {
        return ResponseUtil.success(memberService.loadMember(request));
    }
}
