package outfoot.outfootserver.checkpage.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import outfoot.outfootserver.checkpage.domain.CheckPage;
import outfoot.outfootserver.checkpage.dto.CheckPageRequest;
import outfoot.outfootserver.checkpage.dto.CheckPageResponse;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;
import outfoot.outfootserver.checkpage.repository.CheckPageRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // junit5 & Mockito 연동
class CheckPageServiceTest {

    @Mock CheckPageRepository checkPageRepository;
    @InjectMocks CheckPageService checkPageService;

    public static CheckPageRequest dto;
    public static CheckPage checkPage;

    @BeforeEach
    public void setUp() {
        checkPage = CheckPage.builder()
                .title("목표")
                .intro("한 줄 소개")
                .animal("고양이")
                .build();

        dto = CheckPageRequest.builder()
                .title("목표")
                .intro("한 줄 소개")
                .animalId("1")
                .build();
    }

    @Test
    @DisplayName("[성공] 도장판 생성")
    public void saveCheckPage() throws Exception {
        // static dto
        // given

        // when
        when(checkPageRepository.save(any())).thenReturn(checkPage);
        CheckPageResponse checkPageResponse = checkPageService.saveCheckPage(dto);

        // then
        assertThat(checkPageResponse.animal()).isEqualTo("고양이");
    }

    @Test
    @DisplayName("[예외] 중복 도장판")
    public void duplicateSaveCheckPage() throws Exception {
        // when
        when(checkPageRepository.findByTitle(any())).thenThrow(new CheckPageException(CheckPageErrorCode.CHECKPAGE_DUPLICATION));


        // then
        CheckPageException e = assertThrows(CheckPageException.class, () -> {
            checkPageService.saveCheckPage(dto);
        });

        assertThat(CheckPageErrorCode.CHECKPAGE_DUPLICATION).isEqualTo(e.getCode());
    }

}