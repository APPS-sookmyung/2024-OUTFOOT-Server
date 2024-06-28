package outfoot.outfootserver.checkpage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import outfoot.outfootserver.checkpage.domain.CheckPage;

// 시작일, 제목, 위치
@Builder
public record CheckPageListResponse(@NotBlank String title, String created_at, int animalPosition, String animal) {


    public static CheckPageListResponse toCheckPageList(CheckPage checkPage) {
        return CheckPageListResponse.builder()
                .title(checkPage.getTitle())
                .created_at(checkPage.getCreatedAt())
                .animalPosition(checkPage.getAnimalPosition())
                .animal(checkPage.getAnimal())
                .build();
    }
}
