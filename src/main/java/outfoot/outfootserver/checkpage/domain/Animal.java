package outfoot.outfootserver.checkpage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Animal {
    // 동물 요구사항 변경 가능성 O

    cat("고양이"), dog("강아지");

    private final String animal_name;
}
