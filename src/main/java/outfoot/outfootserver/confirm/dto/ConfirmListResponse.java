package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import outfoot.outfootserver.confirm.domain.Confirm;

@Builder
public record ConfirmListResponse(
        @Schema(description = "인증판 아이디", example = "1") Long id,
        @Schema(description = "인증판 이미지 URL", example = "img/png") String imageUrl,
        @Schema(description = "인증판 순서", example = "1") Long order) {

    public static ConfirmListResponse toConfirmList(Confirm confirm){
        return ConfirmListResponse.builder()
                .id(confirm.getId())
                .imageUrl(confirm.getImageUrl())
                .order(confirm.getOrder())
                .build();
    }
}
