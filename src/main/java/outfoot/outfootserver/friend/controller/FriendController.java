package outfoot.outfootserver.friend.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.friend.dto.AddFriendRequest;
import outfoot.outfootserver.friend.service.FriendService;
import outfoot.outfootserver.member.domain.Member;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    @Autowired
    private EntityManager entityManager;

    private final FriendService friendService;

//    add, search에서 둘 다 쓰길래 전역변수로 놨는데 이것도 괜찮은것인지..
    private Long friendMemberId;

    @PostMapping("/{member_id}")
    public BasicResponse<String> addFriends (@Valid @RequestBody AddFriendRequest dto, @PathVariable Long memberId){
        Long friendId = friendService.addFriend(dto, memberId, friendMemberId); // Friend 엔티티 저장
        return ResponseUtil.success("친구 추가 성공" + friendId);
    }

    @DeleteMapping("/{member_id}")
//    url로 friend_member_id를 받을지 , member_id를 받을지에 따라 다를 듯
    public BasicResponse<String> deleteFriends(@PathVariable Long memberId) {
        friendService.deleteFriend(memberId);
        return ResponseUtil.success("친구 삭제 성공" + memberId);
    }

    @GetMapping("/{member_id}")
    // 오류 나는 경우 추가 필요 + 친추 당한 멤버 객체로 반환(메서드를 서비스에 추가해서 add, search에 넣기)
    public BasicResponse<String> searchFriends(@Valid @RequestParam("code") String searchCode, @PathVariable Long memberId) {
        String sql = "SELECT * FROM Member WHERE code = :searchCode";
        Query query = entityManager.createNativeQuery(sql, Member.class);
        query.setParameter("searchCode", Long.parseLong(searchCode));

        Member member = (Member) query.getSingleResult();
        friendMemberId = member.getId();
        return ResponseUtil.success("친구 검색 성공"+ friendMemberId);
    }
}

