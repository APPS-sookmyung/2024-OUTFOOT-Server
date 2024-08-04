package outfoot.outfootserver;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.files.FileUploader;

@RestController
@Tag(name = "Health Check", description = "API 테스트")
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthCheck {
    private final FileUploader fileUploader;
    private final String TEST_KEY = "test/";

    @GetMapping
    @Operation(summary = "서버 테스트용 API")
    public BasicResponse<String> healthCheck() {
        return ResponseUtil.success("health check");
    }

    @PostMapping("/file")
    @Operation(summary = "서버 파일 업로드 테스트용 API")
    public BasicResponse<String> healthCheckFile (@ModelAttribute MultipartFile file) {
        String url = fileUploader.uploadFile(file, TEST_KEY);
        return ResponseUtil.success(url);
    }
}
