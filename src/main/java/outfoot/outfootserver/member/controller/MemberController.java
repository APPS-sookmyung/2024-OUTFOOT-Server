package outfoot.outfootserver.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.member.dto.SignUpRequest;
import outfoot.outfootserver.member.service.MemberService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 내 정보 확인
    // BasicResponse로 감싸주기
    @PostMapping("/signup")
    public BasicResponse<String> SignUp (@Valid@RequestBody SignUpRequest dto) {
        long memberId = memberService.save(dto);
        return ResponseUtil.success("가입 성공 " + memberId);
    }
}
