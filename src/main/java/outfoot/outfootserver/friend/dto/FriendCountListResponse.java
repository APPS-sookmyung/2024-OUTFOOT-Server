package outfoot.outfootserver.friend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record FriendCountListResponse (
        @Schema(description = "친구 수", example = "메모") Long total,
        @Schema(description = "친구 리스트", example = "정정") List<FriendListResponse> friendLists) {

    public static FriendCountListResponse toFriendCountLists(@NotNull Long total, List<FriendListResponse> friendLists) {
        return FriendCountListResponse.builder()
                .total(total)
                .friendLists(friendLists)
                .build();
    }
}
