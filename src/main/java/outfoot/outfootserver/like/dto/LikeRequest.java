package outfoot.outfootserver.like.dto;

import lombok.Builder;

@Builder
public record LikeRequest(Long memberId, Long checkPageId, Long confirmId) {
}
