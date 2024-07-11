package outfoot.outfootserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import outfoot.outfootserver.common.GlobalExceptionHandler;


@SpringBootTest
public abstract class ControllerTest {
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUpAbstract() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(injectController())
                .setControllerAdvice(GlobalExceptionHandler.class)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

    }

    protected abstract Object injectController();
}
