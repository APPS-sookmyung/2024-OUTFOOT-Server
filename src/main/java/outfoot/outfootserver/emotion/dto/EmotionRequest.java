package outfoot.outfootserver.emotion.dto;

import lombok.Builder;

@Builder
public record EmotionRequest(Long memberId, Long checkPageId, Long confirmId) {
}
