package outfoot.outfootserver.token.Controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import outfoot.outfootserver.common.response.BasicResponse;
import outfoot.outfootserver.common.response.ResponseUtil;
import outfoot.outfootserver.token.Service.TokenService;
import outfoot.outfootserver.token.dto.TokenResponse;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @Operation(summary = "액세스 토큰 재발행")
    @Parameters({
            @Parameter(name = "Authorization", description = "리프레시 토큰", required = true, example = "Bearer <refresh_token>")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "액세스 토큰 재발급 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 리프레시 토큰")
    })
    @GetMapping("/reissue/access-token")
    public BasicResponse<TokenResponse> reissueAccessToken(HttpServletRequest header){
        TokenResponse accessToken = tokenService.reissueAccessToken(header);

        return ResponseUtil.success(accessToken);
    }
}
