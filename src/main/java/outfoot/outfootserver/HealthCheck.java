package outfoot.outfootserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;

@RestController
public class HealthCheck {

    @GetMapping("/health")
    public BasicResponse<String> healthCheck() {
        return ResponseUtil.success("health check");
    }
}
