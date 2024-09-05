package outfoot.outfootserver.checkpage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.checkpage.dto.CheckPageCountListDto;
import outfoot.outfootserver.checkpage.dto.CheckPageRequest;
import outfoot.outfootserver.checkpage.dto.CheckPageResponse;
import outfoot.outfootserver.checkpage.service.CheckPageService;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

@RestController
@RequestMapping("/checkpages")
@RequiredArgsConstructor
@Tag(name = "도장판", description = "CheckPage API")
public class CheckPageController {
    private final CheckPageService checkPageService;
    private final MemberService memberService;

    @PostMapping("/{member_id}")
    @Operation(summary = "도장판 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "도장 메이트 번호를 찾을 수 없습니다."),
    })
    public BasicResponse<CheckPageResponse> saveCheckPage(@Valid @RequestBody CheckPageRequest dto, @PathVariable("member_id") Long memberId) {
        Member member = memberService.loadMember(memberId);
        CheckPageResponse checkPage = checkPageService.saveCheckPage(dto, member);
        return ResponseUtil.success(checkPage);
    }


    @GetMapping("/{member_id}")
    @Operation(summary = "도장판 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도장판 전체 조회에 성공하였습니다."),
    })
    
    public BasicResponse<CheckPageCountListDto> findAllCheckPage(@PathVariable("member_id") Long memberId) {
        Member member = memberService.loadMember(memberId);
        return ResponseUtil.success(checkPageService.findAllCheckPage(member));
    }

    @GetMapping("/{check_page_id}/{member_id}/foot")
    @Operation(summary = "도장판 단건 조회")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도장판 단건 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "도장판을 찾을 수 없습니다."),
    })
    public BasicResponse<CheckPageResponse> findOne(@PathVariable(name = "check_page_id") Long checkPageId, @PathVariable("member_id") Long memberId) {
        Member member = memberService.loadMember(memberId);
        return ResponseUtil.success(checkPageService.findCheckPage(checkPageId, member));
    }

    @DeleteMapping("/{check_page_id}/{member_id}")
    @Operation(summary = "도장판 삭제")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도장판 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "도장판을 찾을 수 없습니다."),
    })
    public BasicResponse<String> deleteCheckPage(@PathVariable(name = "check_page_id") Long checkPageId, @PathVariable("member_id") Long memberId) {
        Member member = memberService.loadMember(memberId);
        Long id = checkPageService.deleteCheckPage(checkPageId, member);
        return ResponseUtil.success("목표 삭제에 성공하였습니다. checkPageId = " + id);
    }
}
