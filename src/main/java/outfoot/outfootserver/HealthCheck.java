package outfoot.outfootserver;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;

@RestController
@Tag(name = "Health Check", description = "API 테스트")
public class HealthCheck {

    @GetMapping("/health")
    @Operation(summary = "서버 테스트용 API")
    public BasicResponse<String> healthCheck() {
        return ResponseUtil.success("health check");
    }
}
