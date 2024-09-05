package outfoot.outfootserver.confirm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.checkpage.service.CheckPageService;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.dto.ConfirmRequest;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.dto.ConfirmUpdateResponse;
import outfoot.outfootserver.confirm.dto.UpdateConfirmRequest;
import outfoot.outfootserver.confirm.service.ConfirmService;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm")
@Tag(name = "인증판", description = "Confirm API")
public class ConfirmController {

    private final ConfirmService confirmService;
    private final CheckPageService checkPageService;
    private final MemberService memberService;
    
    @PostMapping("/{check_page_id}/{member_id}")
    @Operation(summary = "인증판 저장")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "도장판을 찾을 수 없습니다."),
//            @ApiResponse(responseCode = "400", description = "하루 최대 인증판 개수를 초과하였습니다."),
    })
    public BasicResponse<ConfirmResponse> saveConfirm(@Valid @ModelAttribute ConfirmRequest dto, @PathVariable(name = "check_page_id") Long checkPageId, @PathVariable("member_id") Long memberId) {
        Member member = memberService.loadMember(memberId);
        CheckPage checkPage = checkPageService.findById(checkPageId);
        ConfirmResponse confirm = confirmService.saveConfirm(checkPage, dto, member);
        return ResponseUtil.success(confirm);
    }

    @Operation(summary = "인증판 삭제")
    @Parameters({
            @Parameter(name = "confirm id", description = "공백 X", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
    })
    @DeleteMapping("/{id}/{member_id}")
    public BasicResponse<String> deleteConfirm(@PathVariable("id") Long confirmId, @PathVariable("member_id") Long memberId){
        Member member = memberService.loadMember(memberId);
        confirmService.deleteConfirm(confirmId, member);
        return ResponseUtil.success("인증판 삭제 성공");
    }


    @Operation(summary = "인증판 수정")
    @Parameters({
            @Parameter(name = "confirm id", description = "공백 X", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
    })
    @PutMapping("/{id}/{member_id}")
    public BasicResponse<ConfirmUpdateResponse> updateMemo(@PathVariable("id") Long id,
                                                           @PathVariable("member_id") Long memberId,
                                                           @ModelAttribute UpdateConfirmRequest dto){
        Member member = memberService.loadMember(memberId);
        return ResponseUtil.success(confirmService.updateConfirm(id, dto, member));
    }


    @Operation(summary = "인증판 조회")
    @Parameters({
            @Parameter(name = "confirm id", description = "공백 X", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
    })
    @GetMapping("/{id}/{member_id}")
    public BasicResponse<ConfirmResponse> findConfirm(@PathVariable("id") Long id,
                                                      @PathVariable("member_id") Long memberId){
        Member member = memberService.loadMember(memberId);
        return ResponseUtil.success(confirmService.findConfirm(id, member));
    }
}
