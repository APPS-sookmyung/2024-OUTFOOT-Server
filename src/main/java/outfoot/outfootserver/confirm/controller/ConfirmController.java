package outfoot.outfootserver.confirm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.dto.ConfirmListResponse;
import outfoot.outfootserver.confirm.dto.ConfirmRequest;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.dto.UpdateConfirmRequest;
import outfoot.outfootserver.confirm.service.ConfirmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm/{check_page_id}")
public class ConfirmController {

    private final ConfirmService confirmService;


    @PostMapping
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
            @Parameter(name = "memo", description = "사진 설명", example = "메모"),
            @Parameter(name = "order", description = "도장판 내 인증판 순서", example = "1"),
    })
    public BasicResponse<ConfirmResponse> saveConfirm(@PathVariable(name = "check_page_id") Long checkPageId, @Valid @RequestBody ConfirmRequest dto) {
        ConfirmResponse confirm = confirmService.saveConfirm(checkPageId, dto);
        return ResponseUtil.success(confirm);
    }

    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
            @Parameter(name = "order", description = "도장판 내 인증판 순서", example = "1"),
    })
    @DeleteMapping("/{order}")
    public BasicResponse<String> deleteConfirm(@PathVariable(name = "check_page_id") Long checkPageId,
                                               @PathVariable(name = "order") Long order){
        confirmService.deleteConfirm(checkPageId, order);
        return ResponseUtil.success("인증판 삭제 성공");
    }

    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
            @Parameter(name = "order", description = "도장판 내 인증판 순서", example = "1"),
            @Parameter(name = "memo", description = "사진 설명", example = "메모"),
    })
    @PutMapping("/{order}")
    public BasicResponse<ConfirmResponse> updateMemo(@PathVariable(name = "check_page_id") Long checkPageId,
                                                     @PathVariable(name = "order") Long order,
                                                     @RequestBody UpdateConfirmRequest dto){
        ConfirmResponse confirm = confirmService.updateMemo(checkPageId, order, dto.memo());
        return ResponseUtil.success(confirm);
    }
    
    @Parameters({
            @Parameter(name = "check_page_id", description = "공백 X", example = "1"),
            @Parameter(name = "order", description = "도장판 내 인증판 순서", example = "1"),
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
