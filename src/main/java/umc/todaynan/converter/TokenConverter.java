package umc.todaynan.converter;

import org.springframework.stereotype.Component;
import umc.todaynan.oauth2.Token;
import umc.todaynan.web.dto.TokenDTO.TokenResponseDTO;

@Component
public class TokenConverter {

    public  TokenResponseDTO.TokenRefreshDTO toTokenRefreshDTO(Token token) {
        return TokenResponseDTO.TokenRefreshDTO.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }
}
