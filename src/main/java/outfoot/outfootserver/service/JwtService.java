package outfoot.outfootserver.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import outfoot.outfootserver.exception.TokenErrorResult;
import outfoot.outfootserver.exception.TokenException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 액세스 토큰 발행 메서드
    public String generateAccessToken(UUID userId, long expirationMillis){
        log.info("액세스 토큰이 발행되었습니다.");

        return Jwts.builder()
                .claim("userId", userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(this.getSigningKey())
                .compact();
    }

    // 리프레쉬 토큰 발행 메서드
    public String generateRefreshToken(UUID userID, long expirationMillis){
        log.info("리프레쉬 토큰이 발행되었습니다.");

        return Jwts.builder()
                .claim("userId", userID.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .compact();
    }

    public String getTokenFromHeader(String authorizationHeader){
        return authorizationHeader.substring(7);
    }

    public String getUserIdFromToken(String token){
        try{
            String userId = Jwts.parser()
                    .verifyWith(this.getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("userId", String.class);
            log.info("유저 id를 반환");
            return userId;
        }
        catch(JwtException | IllegalArgumentException e){
            log.warn("유효하지 않은 토큰입니다.");
            throw new TokenException(TokenErrorResult.INVALID_TOKEN);
        }
    }

    public boolean isTokenExpired(String token){
        try {
            Date expirationDate = Jwts.parser()
                    .verifyWith(this.getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            log.info("토큰 유효기간 확인");
            return expirationDate.before(new Date());
        }
        catch(JwtException | IllegalArgumentException e){
            log.warn("유효하지 않은 토큰");
            throw new TokenException(TokenErrorResult.INVALID_TOKEN);
        }
    }
}
