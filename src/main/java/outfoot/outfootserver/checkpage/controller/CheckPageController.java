package outfoot.outfootserver.checkpage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.checkpage.dto.CheckPageListResponse;
import outfoot.outfootserver.checkpage.dto.CheckPageRequest;
import outfoot.outfootserver.checkpage.dto.CheckPageResponse;
import outfoot.outfootserver.checkpage.service.CheckPageService;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/checkpages")
@RequiredArgsConstructor
@Tag(name = "도장판", description = "CheckPage API")
public class CheckPageController {
    private final CheckPageService checkPageService;

    @PostMapping
    @Operation(summary = "인증판 생성")
    @Parameters({
            @Parameter(name = "title", description = "공백 X", example = "목표"),
            @Parameter(name = "intro", description = "한 줄 소개", example = "한 줄 소개"),
            @Parameter(name = "animalId", description = "도장 메이트, 공백 X", example = "1")
    })

//    public BasicResponse<String> saveCheckPage(@Valid @RequestBody CheckPageRequest dto, String memberId) {
    public BasicResponse<CheckPageResponse> saveCheckPage(@Valid @RequestBody CheckPageRequest dto) {
        // member 연동이 안 되어 있어 우선 member 없이 checkpage 생성 구현
//        checkPageService.saveCheckPage(member, dto);
        CheckPageResponse checkPage = checkPageService.saveCheckPage(dto);
        return ResponseUtil.success(checkPage);
    }

    @GetMapping
    @Operation(summary = "인증판 전체 조회")
    public BasicResponse<List<CheckPageListResponse>> findAllCheckPage() {
        return ResponseUtil.success(checkPageService.findAllCheckPage());
    }

    @GetMapping("/{check_page_id}/foot")
    @Operation(summary = "인증판 단건 조회")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1")
    })
            public BasicResponse<CheckPageResponse>findOne(@PathVariable(name = "check_page_id") Long checkPageId) {
        return ResponseUtil.success(checkPageService.findCheckPage(checkPageId));
    }

    @DeleteMapping("/{check_page_id}")
    @Operation(summary = "인증판 삭제")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1")
    })
    public BasicResponse<String> deleteCheckPage(@PathVariable(name = "check_page_id") Long checkPageId) {
        Long id = checkPageService.deleteCheckPage(checkPageId);
        return ResponseUtil.success("목표 삭제에 성공하였습니다. checkPageId = " + id);
    }
}
