package outfoot.outfootserver.emotion.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.service.ConfirmService;
import outfoot.outfootserver.emotion.service.DislikeService;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dislike/{member_id}/{confirm_id}")
@Tag(name = "부정", description = "Dislike API")
public class DislikeController {

    private final DislikeService dislikeService;
    private final MemberService memberService;
    private final ConfirmService confirmService;

    @PostMapping
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "confirm_id", example = "1"),
    })
    public BasicResponse<String> addDislike(@Valid @PathVariable(name = "member_id") Long memberId, @PathVariable(name = "confirm_id") Long confirmId) {
        Member member = memberService.loadMember(memberId);
        Confirm confirm = confirmService.findById(confirmId);

        dislikeService.addDislike(member, confirm);
        return ResponseUtil.success("부정 추가에 성공하였습니다. Confirm id = " + confirmId);
    }

    @DeleteMapping
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "confirm_id", example = "1"),
    })
    public BasicResponse<String> deleteDislike(@Valid @PathVariable(name = "member_id") Long memberId, @PathVariable(name = "confirm_id") Long confirmId) {
        Member member = memberService.loadMember(memberId);
        Confirm confirm = confirmService.findById(confirmId);
        dislikeService.cancelDislike(member, confirm);
        return ResponseUtil.success("부정 취소에 성공하였습니다. Confirm id = " + confirmId);
    }
}
