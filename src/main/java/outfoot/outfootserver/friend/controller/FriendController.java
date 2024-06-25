package outfoot.outfootserver.friend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.service.FriendService;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping
    public BasicResponse<String> addFriends (@Valid @RequestBody AddFriendRequest dto){
        long friendId = friendService.save(dto); // Friend 엔티티 저장
        return ResponseUtil.success("친구 추가 성공" + friendId);
    }

    @DeleteMapping("/{friend_id}")
    public BasicResponse<String> deleteFriends(@PathVariable long friend_id) {
        friendService.delete(friend_id);
        return ResponseUtil.success("친구 삭제 성공" + friend_id);

    }
}
