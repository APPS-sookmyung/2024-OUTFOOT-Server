package outfoot.outfootserver.emotion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.service.ConfirmService;
import outfoot.outfootserver.emotion.service.LikeService;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like/{confirm_id}")
@Tag(name = "인정", description = "Like API")
public class LikeController {
    private final LikeService likeService;
    private final MemberService memberService;
    private final ConfirmService confirmService;

    @PostMapping
    @Operation(summary = "인정 추가")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "confirm_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인정 추가에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "인정 버튼을 중복으로 누를 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "중복으로 버튼을 누를 수 없습니다. (인정, 부정)"),
    })
    public BasicResponse<String> addLike(HttpServletRequest request, @PathVariable(name = "confirm_id") Long confirmId) {
        Member member = memberService.loadMember(request);
        Confirm confirm = confirmService.findById(confirmId);
        likeService.addLike(member, confirm);
        return ResponseUtil.success("인정 추가에 성공하였습니다. Confirm id = " + confirmId);
    }
    
    @DeleteMapping
    @Operation(summary = "인정 삭제")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "confirm_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인정 취소에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
    })
    public BasicResponse<String> deleteLike(HttpServletRequest request, @PathVariable(name = "confirm_id") Long confirmId) {
        Member member = memberService.loadMember(request);
        Confirm confirm = confirmService.findById(confirmId);

        likeService.cancelLike(member, confirm);
        return ResponseUtil.success("인정 취소에 성공하였습니다. Confirm id = " + confirmId);
    }
}
