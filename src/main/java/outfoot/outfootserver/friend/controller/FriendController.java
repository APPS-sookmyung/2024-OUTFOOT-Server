package outfoot.outfootserver.friend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.friend.dto.FriendCountListResponse;
import outfoot.outfootserver.friend.service.FriendService;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.service.MemberService;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
@Tag(name="친구 CRUD", description = "Friend API")
public class FriendController {
    private final FriendService friendService;
    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "친구 추가")
    @Parameters({
            @Parameter(name = "member_id", example = "1"),
            @Parameter(name = "code", description = "친구 코드", example = "ABC"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구 추가에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 친구입니다."),
            @ApiResponse(responseCode = "400", description = "자기 자신을 친구로 추가할 수 없습니다."),
    })
    public BasicResponse<String> addFriends (HttpServletRequest request, @Valid @RequestParam("code") String searchCode){
        //toMember = 친추를 받은(친구) , fromMember = 친추를 한(본인)
        Member toMember = memberService.searchFriend(searchCode);
        Member fromMember = memberService.loadMember(request);
        friendService.addFriend(fromMember, toMember);
        return ResponseUtil.success("친구 추가 성공: " + fromMember.getId() + " -> " + toMember.getId());
    }

    @DeleteMapping("/{member_id}/{friend_id}")
    @Operation(summary = "친구 추가")
    @Parameters({
            @Parameter(name = "friend_id", example = "1"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구 삭제에 성공하였습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 친구입니다."),
    })
    public BasicResponse<String> deleteFriends( @PathVariable("member_id") Long memberId, @PathVariable("friend_id") Long friendId) {
        Member member = memberService.loadMember(memberId);
        friendService.deleteFriend(member, friendId);
        return ResponseUtil.success("친구 삭제 성공");
    }
    
//    @GetMapping
//    @Operation(summary = "친구 조회")
//    @Parameters({
//            @Parameter(name = "code", description = "친구 코드", example = "ABC")
//    })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "친구 검색에 성공하였습니다."),
//            @ApiResponse(responseCode = "400", description = "존재하지 않는 회원입니다."),
//    })
//    public BasicResponse<String> searchFriends(@Valid @RequestParam("code") String searchCode) {
//        Member member = memberService.searchFriend(searchCode);
//        return ResponseUtil.success("친구 검색 성공: "+ member.getId());
//    }

    @Operation(summary = "친구 전체 조회")
    @GetMapping
    @Parameters({
            @Parameter(name = "member_id", example = "1")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "친구 전체 조회에 성공하였습니다."),
    })
    public BasicResponse<FriendCountListResponse> findAllFriend(HttpServletRequest request){
        Member member = memberService.loadMember(request);
        return ResponseUtil.success(friendService.findAllFriend(member.getId()));
    }

}

