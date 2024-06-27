package outfoot.outfootserver.friend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import outfoot.outfootserver.friend.domain.Friend;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchFriendRequest {
    @NotBlank
    private String friendCode;
}


