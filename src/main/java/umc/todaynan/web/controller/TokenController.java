package umc.todaynan.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.converter.TokenConverter;
import umc.todaynan.oauth2.Token;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.web.dto.TokenDTO.TokenResponseDTO;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    @GetMapping("/token")
    public OAuth2AccessToken token(@RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient oAuth2AuthorizedClient){
        return oAuth2AuthorizedClient.getAccessToken();
    }


    @GetMapping("/token/expired")
    public ApiResponse<TokenResponseDTO.TokenRefreshDTO> refreshAuth(HttpServletRequest request) {
        String token = request.getHeader("Refresh");
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            Token newToken = tokenService.generateToken(email, "USER");

            return ApiResponse.of(SuccessStatus.TOKEN_REFRESHED, tokenConverter.toTokenRefreshDTO(newToken));
        }

        throw new RuntimeException("유효한 refresh 토큰이 아닙니다.");
    }


    @GetMapping("/token/test")
    public String test(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        String email = tokenService.getUid(token);
        System.out.println(email);
        return email;
    }
}
