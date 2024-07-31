package outfoot.outfootserver.emotion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.emotion.dto.EmotionRequest;
import outfoot.outfootserver.emotion.service.EmotionManageService;
import outfoot.outfootserver.emotion.service.LikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;
    private final EmotionManageService emotionManageService;

//    @PostMapping
    @GetMapping("/{member_id}/{check_page_id}/{confirm_id}")
    public BasicResponse<String> addLike(@PathVariable Long member_id, @PathVariable Long check_page_id, @PathVariable Long confirm_id) {
        EmotionRequest dto = emotionManageService.loadLikeInfo(member_id, check_page_id, confirm_id);
        likeService.addLike(dto);
        return ResponseUtil.success("인정 추가에 성공하였습니다. Confirm id = " + confirm_id);
    }
    
    @DeleteMapping("/{member_id}/{check_page_id}/{confirm_id}")
    public BasicResponse<String> deleteLike(@PathVariable Long member_id, @PathVariable Long check_page_id, @PathVariable Long confirm_id) {
        EmotionRequest dto = emotionManageService.loadLikeInfo(member_id, check_page_id, confirm_id);

        likeService.cancelLike(dto);
        return ResponseUtil.success("인정 취소에 성공하였습니다. Confirm id = " + confirm_id);
    }
}
