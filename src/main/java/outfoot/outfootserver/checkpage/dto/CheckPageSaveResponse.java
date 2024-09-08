package outfoot.outfootserver.checkpage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.confirm.dto.ConfirmImageResponse;

import java.util.List;

@Builder
public record CheckPageSaveResponse(
        @Schema(description = "도장판 아이디", example = "1") @NotNull Long id,
        @Schema(description = "도장판 목표", example = "물 마시기") @NotBlank String title,
        @Schema(description = "도장판 목표", example = "하루에 한 잔 물 마시기") String intro,
        @Schema(description = "생성 일자", example = "2024-08-03 15:51:46") String createdAt,
        @Schema(description = "도장판 메이트 위치", example = "1") @NotBlank int animalPosition,
        @Schema(description = "도장판 메이트 종류", example = "고양이") @NotBlank String animal
) {
    public static CheckPageSaveResponse toCheckPage(CheckPage checkPage) {
        return CheckPageSaveResponse.builder()
                .id(checkPage.getId())
                .title(checkPage.getTitle())
                .intro(checkPage.getIntro())
                .createdAt(checkPage.getCreatedAt())
                .animalPosition(checkPage.getConfirms().size())
                .animal(checkPage.getAnimal())
                .build();
    }
}
