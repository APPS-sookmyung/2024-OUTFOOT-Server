package outfoot.outfootserver.checkpage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import outfoot.outfootserver.checkpage.domain.CheckPage;

// 시작일, 제목, 위치
@Builder
public record CheckPageListResponse(
        @Schema(description = "도장판 아이디", example = "1") @NotNull Long id,
        @Schema(description = "도장판 목표", example = "목표") @NotBlank String title,
        @Schema(description = "생성일자", example = "2024-08-03 15:51:46") String createdAt,
        @Schema(description = "동물 위치", example = "1") int animalPosition,
        @Schema(description = "동물 종류", example = "고양이") String animal) {


    public static CheckPageListResponse toCheckPageList(CheckPage checkPage) {
        return CheckPageListResponse.builder()
                .id(checkPage.getId())
                .title(checkPage.getTitle())
                .createdAt(checkPage.getCreatedAt())
                .animalPosition(checkPage.getAnimalPosition())
                .animal(checkPage.getAnimal())
                .build();
    }
}
