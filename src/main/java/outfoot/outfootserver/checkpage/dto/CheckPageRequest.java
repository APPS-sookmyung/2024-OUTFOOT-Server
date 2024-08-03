package outfoot.outfootserver.checkpage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.member.domain.Member;

@Builder
public record CheckPageRequest(
        @Schema(description = "도장판 목표", example = "물 마시기") @NotBlank String title,
        @Schema(description = "목표 설명", example = "하루에 한 잔 물 마시기") String intro,
        @Schema(description = "도장 메이트", example = "1") @NotBlank String animalId) {

//    public static CheckPage toCheckPage(Member member, CheckPageRequest dto) {
    public static CheckPage toCheckPage(CheckPageRequest dto, String animal) {
        return CheckPage.builder()
//                .member(member)
                .title(dto.title())
                .intro(dto.intro())
                .animal(animal)
                .build();
    }

    }