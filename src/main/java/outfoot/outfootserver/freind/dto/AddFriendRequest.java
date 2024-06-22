package outfoot.outfootserver.freind.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.freind.domain.Friend;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddFriendRequest {
    @NotBlank
    private String nickname;
    @NotBlank

    public static Friend toFriend(AddFriendRequest dto) {
        return Friend.builder()
                .nickname(dto.nickname)
                .build();
    }
}
