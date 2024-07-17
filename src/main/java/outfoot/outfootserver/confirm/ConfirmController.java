package outfoot.outfootserver.confirm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.confirm.service.ConfirmService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confirm") // -> 이거 맞는지 확인 안 함.
public class ConfirmController {

    private final ConfirmService confirmService;

    @PostMapping
    public BasicResponse<String> save() {
        confirmService.save();
        return ResponseUtil.success("인증판 생성에 성공하였습니다.");
    }
}
