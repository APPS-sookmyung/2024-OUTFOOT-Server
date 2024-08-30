package outfoot.outfootserver.checkpage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Animal {
    // 동물 요구사항 변경 가능성 O

    cat(1, "고양이"), dog(2, "강아지");

    private final int animalType;
    private final String animalName;

    public static Animal of(int animal_type) {
        return Arrays.stream(values())
                .filter(type -> type.animalType == animal_type)
                .findAny()
                .orElseThrow(() -> new CheckPageException(CheckPageErrorCode.ANIMAL_NOT_FOUND));
    }
}
