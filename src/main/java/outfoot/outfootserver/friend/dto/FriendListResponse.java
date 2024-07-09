package outfoot.outfootserver.friend.dto;

import lombok.Builder;
import outfoot.outfootserver.friend.domain.Friend;
import outfoot.outfootserver.member.domain.Member;

@Builder
public record FriendListResponse(Long fromMember, Long toMember, String nickname) {

    public static FriendListResponse toFriendList(Friend friend){
        return FriendListResponse.builder()
                .fromMember(friend.getFromMember().getId())
                .toMember(friend.getToMember().getId())
                .nickname(friend.getNickname())
                .build();
    }

}
