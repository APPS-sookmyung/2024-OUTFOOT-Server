package outfoot.outfootserver.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.member.domain.Member;

@Builder
public record FriendListResponse(
        @Schema(description = "친구 아이디", example = "1") Long id,
        @Schema(description = "친구 닉네임", example = "정정") String nickname,
        @Schema(description = "친구 한 줄 소개", example = "안녕하세요") String intro) {

    public static FriendListResponse toFriendList(Friend friend){
        return FriendListResponse.builder()
                .id(friend.getToMember().getId())
                .nickname(friend.getNickname())
                .intro(friend.getToMember().getMyIntro())
                .build();
    }

}
