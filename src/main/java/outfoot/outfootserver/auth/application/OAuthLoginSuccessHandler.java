package outfoot.outfootserver.auth.application;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.UriEncoder;
import outfoot.outfootserver.auth.dto.KakaoUserInfo;
import outfoot.outfootserver.auth.dto.NaverUserInfo;
import outfoot.outfootserver.auth.dto.OAuth2UserInfo;
import outfoot.outfootserver.member.domain.Member;
import outfoot.outfootserver.member.repository.MemberRepository;
import outfoot.outfootserver.service.JwtService;
import outfoot.outfootserver.token.domain.RefreshToken;
import outfoot.outfootserver.token.repository.RefreshTokenRepository;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${jwt.redirect}")
    private String REDIRECT_URI;

    @Value("${jwt.access-token.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh-token.expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;

    private OAuth2UserInfo oAuth2UserInfo = null;

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)  authentication;
        final String provider = token.getAuthorizedClientRegistrationId();


        switch (provider){
            case "kakao":
                log.info("카카오 로그인 요청");
                oAuth2UserInfo = new KakaoUserInfo(token.getPrincipal().getAttributes());
                break;

            case "naver":
                log.info("네이버 로그인 요청");
                oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) token.getPrincipal().getAttributes().get("response"));
                break;

            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
        
        String providerId = oAuth2UserInfo.getProviderId();
        String name = oAuth2UserInfo.getName();

        Member member = memberRepository.findByProviderId((providerId));
        
        if(member == null){ // 신규 유저인 경우
            log.info("신규 유저입니다. 등록을 진행합니다.");
            
            member = Member.builder()
                    .userId(UUID.randomUUID())
                    .username(name)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberRepository.save(member);
        }
        else {
            log.info("기존 유저입니다.");
            refreshTokenRepository.deleteByUserId(member.getUserId());
        }

        log.info("유저 이름 : {}", name);
        log.info("provider : {}", provider);
        log.info("provider_id : {}", providerId);

        String refreshToken = jwtService.generateRefreshToken(member.getUserId(), REFRESH_TOKEN_EXPIRATION_TIME);

        RefreshToken newRefreshToken = RefreshToken.builder()
                .userId(member.getUserId())
                .token(refreshToken)
                .build();
        refreshTokenRepository.save(newRefreshToken);

        String accessToken = jwtService.generateAccessToken(member.getUserId(), ACCESS_TOKEN_EXPIRATION_TIME);

        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String redirectUri = String.format(REDIRECT_URI, encodedName, accessToken, refreshToken);
        getRedirectStrategy().sendRedirect(request, response, redirectUri);

    }
}
