package outfoot.outfootserver.auth.application;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import outfoot.outfootserver.exception.TokenErrorResult;
import outfoot.outfootserver.exception.TokenException;
import outfoot.outfootserver.service.JwtService;
import outfoot.outfootserver.token.domain.RefreshToken;
import outfoot.outfootserver.token.dto.response.TokenResponse;
import outfoot.outfootserver.token.repository.RefreshTokenRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Value("${jwt.access-token.expiration-time")
    private long ACCESS_TOKEN_EXPIRATION_TIME; //액세스 토큰 유효기간

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Override
    public TokenResponse reissueAccessToken(String authorizationHeader) {
        String refreshToken = jwtService.getTokenFromHeader(authorizationHeader);
        String userId = jwtService.getUserIdFromToken(refreshToken);
        RefreshToken existRefreshToken = refreshTokenRepository.findByUserId(UUID.fromString(userId));
        String accessToken = null;

        if (!existRefreshToken.getToken().equals(refreshToken) || jwtService.isTokenExpired(refreshToken)){
            throw new TokenException(TokenErrorResult.INVALID_REFRESH_TOKEN);
        }
        else{
            accessToken = jwtService.generateAccessToken(UUID.fromString(userId), ACCESS_TOKEN_EXPIRATION_TIME);
        }
        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
