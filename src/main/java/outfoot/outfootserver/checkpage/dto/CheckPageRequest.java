package outfoot.outfootserver.checkpage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.member.domain.Member;

@Builder
public record CheckPageRequest(
        @Schema(description = "도장판 목표", example = "물 마시기") @NotBlank String title,
        @Schema(description = "한 줄 소개", example = "하루에 한 잔 물 마시기") String intro,
        @Schema(description = "도장 메이트", example = "1") @NotNull int animalId) {

    public static CheckPage toCheckPage(CheckPageRequest dto, String animal) {
        return CheckPage.builder()
                .title(dto.title())
                .intro(dto.intro())
                .animal(animal)
                .build();
    }

    }