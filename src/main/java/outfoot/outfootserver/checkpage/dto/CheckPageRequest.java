package outfoot.outfootserver.checkpage.dto;

import jakarta.validation.constraints.NotBlank;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.member.domain.Member;

public record CheckPageRequest(@NotBlank String title, String intro, @NotBlank String animalId) {

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