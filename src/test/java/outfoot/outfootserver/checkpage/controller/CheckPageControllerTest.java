package outfoot.outfootserver.checkpage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import outfoot.outfootserver.ControllerTest;
import outfoot.outfootserver.checkpage.dto.CheckPageRequest;
import outfoot.outfootserver.checkpage.dto.CheckPageResponse;
import outfoot.outfootserver.checkpage.exception.CheckPageErrorCode;
import outfoot.outfootserver.checkpage.exception.CheckPageException;
import outfoot.outfootserver.checkpage.service.CheckPageService;
import outfoot.outfootserver.common.GlobalExceptionHandler;

import java.util.Locale;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class CheckPageControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CheckPageService checkPageService;

    private static CheckPageRequest checkPageRequest;
    private static CheckPageResponse checkPage;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(injectController())
                .setControllerAdvice(GlobalExceptionHandler.class)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        checkPageRequest = CheckPageRequest.builder()
                .title("목표")
                .intro("한 줄 소개")
                .animalId("1")
                .build();

        checkPage = CheckPageResponse.builder()
                .title("목표")
                .intro("한 줄 소개")
                .createdAt("2024-07-07")
                .animalPosition(1)
                .animal("고양이")
                .build();
    }

    @Test
    @DisplayName("[성공] 도장판 생성")
    public void saveCheckPage() throws Exception {
        // when
        when(checkPageService.saveCheckPage(any())).thenReturn(checkPage);

        // then
        String body = objectMapper.writeValueAsString(checkPageRequest);

        ResultActions perform = mockMvc.perform(post("/checkpages")
                .contentType(MediaType.APPLICATION_JSON)
                .locale(Locale.KOREA)
                .content(body));


        // ControllerTest
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.title").value(checkPage.title()))
                .andExpect(jsonPath("$.response.intro").value(checkPage.intro()))
                .andExpect(jsonPath("$.response.createdAt").value(checkPage.createdAt()))
                .andExpect(jsonPath("$.response.animalPosition").value(checkPage.animalPosition()))
                .andExpect(jsonPath("$.response.animal").value(checkPage.animal()));
    }

    @Test
    @DisplayName("[예외] 도장판 유효성 검사 실패")
    public void validException() throws Exception {
        // given
        checkPageRequest = CheckPageRequest.builder()
                .title("  ")
                .intro("한 줄 소개")
                .animalId("1")
                .build();

        // then
        String body = objectMapper.writeValueAsString(checkPageRequest);

        ResultActions perform = mockMvc.perform(post("/checkpages")
                .contentType(MediaType.APPLICATION_JSON)
                .locale(Locale.KOREA)
                .content(body));

        perform.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.errorCode").value("BAD_INPUT"))
                .andExpect(jsonPath("$.response.errorMessage").value("입력이 올바르지 않습니다."))
                .andExpect(jsonPath("$.response.errors.title").value("공백일 수 없습니다"));
    }

    //    @Override
    protected Object injectController() {
        return new CheckPageController(checkPageService);
    }
}