package outfoot.outfootserver.confirm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import outfoot.outfootserver.confirm.domain.Confirm;

import java.util.List;

@Builder
public record ConfirmImageResponse(
        @Schema(description = "인증판 아이디", example = "1") Long id,
        @Schema(description = "인증판 이미지", example = "img/png") String imageUrl) {

    public static ConfirmImageResponse toConfirm(Confirm confirm) {
        return ConfirmImageResponse.builder()
                .id(confirm.getId())
                .imageUrl(confirm.getImageUrl())
                .build();
    }
}
