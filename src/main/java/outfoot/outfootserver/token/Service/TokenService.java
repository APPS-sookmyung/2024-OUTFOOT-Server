package outfoot.outfootserver.token.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import outfoot.outfootserver.exception.TokenErrorCode;
import outfoot.outfootserver.exception.TokenException;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.exception.AuthErrorCode;
import outfoot.outfootserver.member.exception.AuthException;
import outfoot.outfootserver.member.repository.MemberRepository;
import outfoot.outfootserver.service.JwtService;
import outfoot.outfootserver.token.domain.RefreshToken;
import outfoot.outfootserver.token.dto.TokenResponse;
import outfoot.outfootserver.token.repository.RefreshTokenRepository;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${jwt.access-token.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private  final MemberRepository memberRepository;

    public TokenResponse reissueAccessToken(HttpServletRequest header){
        String refreshToken = jwtService.getTokenFromHeader(header);
        log.info("refreshtoken : " + refreshToken);
        String username = jwtService.getUsernameFromToken(refreshToken);
        RefreshToken existRefreshToken = refreshTokenRepository.findByUsername(UUID.fromString(username));
        String accessToken = null;

        if (!existRefreshToken.getToken().equals(refreshToken) || jwtService.isTokenExpired(refreshToken)){
            throw new TokenException(TokenErrorCode.INVALID_REFRESH_TOKEN);
        }
        else {
            Member member = memberRepository.findByUsername(UUID.fromString(username))
                    .orElseThrow(() -> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));;
            String nickname = member.getNickname();
            accessToken = jwtService.generateAccessToken(UUID.fromString(username), nickname,ACCESS_TOKEN_EXPIRATION_TIME);
        }

        return TokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

}
