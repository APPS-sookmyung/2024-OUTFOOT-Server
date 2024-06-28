package outfoot.outfootserver.checkpage.controller;

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
public class CheckPageController {

    private final CheckPageService checkPageService;
    @PostMapping
//    public BasicResponse<String> saveCheckPage(@Valid @RequestBody CheckPageRequest dto, String memberId) {
    public BasicResponse<String> saveCheckPage(@Valid @RequestBody CheckPageRequest dto) {
        // member 연동이 안 되어 있어 우선 member 없이 checkpage 생성 구현
//        checkPageService.saveCheckPage(member, dto);
        Long checkPageId = checkPageService.saveCheckPage(dto);
        return ResponseUtil.success("목표 생성에 성공하였습니다. checkPageId = " + checkPageId);
    }

    @GetMapping
    public BasicResponse<List<CheckPageListResponse>> findAllCheckPage() {
        return ResponseUtil.success(checkPageService.findAllCheckPage());
    }

    @GetMapping("/{check_page_id}/foot")
    public BasicResponse<CheckPageResponse> findOne(@Valid @PathVariable(name = "check_page_id") Long checkPageId) {
        return ResponseUtil.success(checkPageService.findCheckPage(checkPageId));
    }
}
