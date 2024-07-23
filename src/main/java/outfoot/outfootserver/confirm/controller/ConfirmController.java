package outfoot.outfootserver.confirm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.confirm.dto.ConfirmRequest;
import outfoot.outfootserver.confirm.dto.ConfirmResponse;
import outfoot.outfootserver.confirm.service.ConfirmService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm/{check_page_id}")
public class ConfirmController {

    private final ConfirmService confirmService;

    @PostMapping
    public BasicResponse<ConfirmResponse> save(@Valid @RequestBody ConfirmRequest dto) {
        ConfirmResponse confirm = confirmService.saveConfirm(dto);
        return ResponseUtil.success(confirm);
    }

    @GetMapping("/{confirm_id}")
    public BasicResponse<ConfirmResponse> findConfirm(@PathVariable(name = "confirm_id") Long confirmId){
        ConfirmResponse confirm = confirmService.findConfirm(confirmId);
        return ResponseUtil.success(confirm);
    }

    @DeleteMapping("/{confirm_id}")
    public BasicResponse<String> deleteConfirm(@PathVariable(name = "confirm_id") Long confirmId){
        confirmService.deleteConfirm(confirmId);
        return ResponseUtil.success("인증판 삭제 성공" + confirmId);
    }

    @PutMapping("/{confirm_id}")
    public BasicResponse<ConfirmResponse> updateMemo(@PathVariable(name = "confirm_id") Long confirmId, @RequestBody String memo){
        ConfirmResponse confirm = confirmService.updateMemo(confirmId, memo);
        return ResponseUtil.success(confirm);
    }
}
