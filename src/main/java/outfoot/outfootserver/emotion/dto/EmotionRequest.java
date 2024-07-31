package outfoot.outfootserver.emotion.dto;

import lombok.Builder;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.domain.Confirm;
import outfoot.outfootserver.member.domain.Member;

@Builder
public record EmotionRequest(Member member, CheckPage checkPage, Confirm confirm) {
}
