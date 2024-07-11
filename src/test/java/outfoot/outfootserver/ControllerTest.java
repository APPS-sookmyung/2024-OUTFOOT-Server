package outfoot.outfootserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import outfoot.outfootserver.common.GlobalExceptionHandler;

//@ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class) // Spring TestContextFramework junit5 포함
public abstract class ControllerTest {
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(injectController())
                .setControllerAdvice(GlobalExceptionHandler.class)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

    }

    protected abstract Object injectController();
}
