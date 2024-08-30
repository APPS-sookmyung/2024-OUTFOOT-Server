package outfoot.outfootserver.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record MyProfileResponse(
        @Schema(description = "이름", example = "주아정") String name,
        @Schema(description = "한 줄 소개", example = "안녕하세요.") String myIntro,
        @Schema(description = "친구 추가용 코드", example = "ABC") String code
) {
}
