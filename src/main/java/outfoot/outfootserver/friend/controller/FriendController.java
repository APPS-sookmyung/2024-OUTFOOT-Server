package outfoot.outfootserver.friend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.exception.AuthErrorCode;
import outfoot.outfootserver.friend.exception.AuthException;
import outfoot.outfootserver.friend.repository.FriendRepository;
import outfoot.outfootserver.friend.service.FriendService;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.repository.MemberRepository;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;
    private final MemberRepository memberRepository;

    @PostMapping("/{member_id}")
    public BasicResponse<String> addFriends (@Valid @RequestParam("code") String searchCode, @PathVariable("member_id") Long memberId ){
        Member fromMember = friendService.searchFriend(searchCode);
        Member toMember = memberRepository.findById(memberId).orElseThrow(() -> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));
        friendService.addFriend(fromMember, toMember);
        return ResponseUtil.success("친구 추가 성공");

//        return ResponseUtil.success("친구 추가 성공" + friendId);
    }

    @DeleteMapping("/{member_id}")
    public BasicResponse<String> deleteFriends(@PathVariable Long memberId) {
        friendService.deleteFriend(memberId);
        return ResponseUtil.success("친구 삭제 성공" + memberId);
    }

    @GetMapping("/{member_id}")
    public BasicResponse<String> searchFriends(@Valid @RequestParam("code") String searchCode, @PathVariable("member_id") Long memberId) {
        Member member = friendService.searchFriend(searchCode);
        return ResponseUtil.success("친구 검색 성공"+ member);
    }
}

