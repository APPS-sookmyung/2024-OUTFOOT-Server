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
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.dto.ConfirmRequest;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.dto.UpdateConfirmRequest;
import outfoot.outfootserver.confirm.service.ConfirmService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm/{check_page_id}")
@Tag(name = "인증판", description = "Confirm API")
public class ConfirmController {

    private final ConfirmService confirmService;
    
    @PostMapping
    @Operation(summary = "인증판 저장")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 생성에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "400", description = "인증판의 개수가 초과되었습니다."),
            @ApiResponse(responseCode = "400", description = "하루 최대 인증판 개수를 초과하였습니다."),
    })
    public BasicResponse<ConfirmResponse> saveConfirm(@PathVariable(name = "check_page_id") Long checkPageId, @Valid @RequestBody ConfirmRequest dto) {
        ConfirmResponse confirm = confirmService.saveConfirm(checkPageId, dto);
        return ResponseUtil.success(confirm);
    }

    @Operation(summary = "인증판 삭제")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
            @Parameter(name = "order", description = "도장판 내 인증판 순서", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
    })
    @DeleteMapping("/{order}")
    public BasicResponse<String> deleteConfirm(@PathVariable(name = "check_page_id") Long checkPageId,
                                               @PathVariable(name = "order") Long order){
        confirmService.deleteConfirm(checkPageId, order);
        return ResponseUtil.success("인증판 삭제 성공");
    }


    @Operation(summary = "인증판 수정")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
            @Parameter(name = "order", description = "도장판 내 인증판 순서", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 수정에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
    })
    @PutMapping("/{order}")
    public BasicResponse<ConfirmResponse> updateMemo(@PathVariable(name = "check_page_id") Long checkPageId,
                                                     @PathVariable(name = "order") Long order,
                                                     @RequestBody UpdateConfirmRequest dto){
        ConfirmResponse confirm = confirmService.updateConfirm(checkPageId, order, dto.memo(), dto.image());
        return ResponseUtil.success(confirm);
    }


    @Operation(summary = "인증판 조회")
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
            @Parameter(name = "order", description = "도장판 내 인증판 순서", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증판 조회에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "인증판을 찾을 수 없습니다."),
    })
    @GetMapping("/{order}")
    public BasicResponse<ConfirmResponse> findConfirm(@PathVariable(name = "check_page_id") Long checkPageId,
                                                      @PathVariable(name = "order") Long order){
        ConfirmResponse confirm = confirmService.findConfirm(checkPageId, order);
        return ResponseUtil.success(confirm);
    }

//    @GetMapping
//    public BasicResponse<List<ConfirmListResponse>> findAllConfirm(){
//        return ResponseUtil.success(confirmService.findAllConfirm());
//    }
}
