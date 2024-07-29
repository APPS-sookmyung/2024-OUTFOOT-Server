package outfoot.outfootserver.confirm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.dto.ConfirmListResponse;
import outfoot.outfootserver.confirm.dto.ConfirmRequest;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.service.ConfirmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm/{check_page_id}")
public class ConfirmController {

    private final ConfirmService confirmService;

    @PostMapping
    public BasicResponse<ConfirmResponse> saveConfirm(@PathVariable(name = "check_page_id") Long checkPageId, @Valid @RequestBody ConfirmRequest dto) {
        ConfirmResponse confirm = confirmService.saveConfirm(checkPageId, dto);
        return ResponseUtil.success(confirm);
    }

    @DeleteMapping("/{order}")
    public BasicResponse<String> deleteConfirm(@PathVariable(name = "check_page_id") Long checkPageId,
                                               @PathVariable(name = "order") int order){
        confirmService.deleteConfirm(checkPageId, order);
        return ResponseUtil.success("인증판 삭제 성공");
    }

    @PutMapping("/{confirm_id}")
    public BasicResponse<ConfirmResponse> updateMemo(@PathVariable(name = "confirm_id") Long confirmId,
                                                     @RequestBody String memo){
        ConfirmResponse confirm = confirmService.updateMemo(confirmId, memo);
        return ResponseUtil.success(confirm);
    }

    @GetMapping("/{order}")
    public BasicResponse<ConfirmResponse> findConfirm(@PathVariable(name = "check_page_id") Long checkPageId,
                                                      @PathVariable(name = "order") int order){
        ConfirmResponse confirm = confirmService.findConfirm(checkPageId, order);
        return ResponseUtil.success(confirm);
    }

    @GetMapping
    public BasicResponse<List<ConfirmListResponse>> findAllConfirm(){
        return ResponseUtil.success(confirmService.findAllConfirm());
    }
}
