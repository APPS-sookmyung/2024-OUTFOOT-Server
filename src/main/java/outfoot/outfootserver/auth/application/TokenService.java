package outfoot.outfootserver.auth.application;

import outfoot.outfootserver.token.dto.response.TokenResponse;

public interface TokenService {
    TokenResponse reissueAccessToken(String authorizationHeader);
}
