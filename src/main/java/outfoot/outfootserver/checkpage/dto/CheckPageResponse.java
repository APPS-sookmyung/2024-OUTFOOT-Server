package outfoot.outfootserver.checkpage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import outfoot.outfootserver.checkpage.domain.CheckPage;

@Builder
public record CheckPageResponse(@NotBlank String title, String intro, String createdAt, @NotBlank int animalPosition, @NotBlank String animal) {

    public static CheckPageResponse toCheckPage(CheckPage checkPage) {
        return CheckPageResponse.builder()
                .title(checkPage.getTitle())
                .intro(checkPage.getIntro())
                .createdAt(checkPage.getCreatedAt())
                .animalPosition(checkPage.getAnimalPosition())
                .animal(checkPage.getAnimal())
                .build();
    }
}
