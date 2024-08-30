package outfoot.outfootserver.checkpage.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AnimalTest {

    @Test
    @DisplayName("[성공] 동물 이름 반환")
    public void findAnimalType() throws Exception {
        // when
        Animal animal = Animal.of(1);

        // then
        assertThat(animal.getAnimalName()).isEqualTo("고양이");
    }

    @Test
    @DisplayName("[예외] 동물 존재 X")
    public void notFoundAnimal() throws Exception {
        CheckPageException e = assertThrows(CheckPageException.class, () -> {
            Animal.of(3);
        });

        assertThat(CheckPageErrorCode.ANIMAL_NOT_FOUND).isEqualTo(e.getCode());
    }
}