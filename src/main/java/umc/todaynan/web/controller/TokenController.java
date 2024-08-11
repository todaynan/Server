package umc.todaynan.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
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

    /**
     * 서비스 이용중 JWT 만료시 Front에게 알리는 API
     */
    @GetMapping("/token/expired")
    public ApiResponse jwtTokenExpired() {
        return ApiResponse.onFailure(ErrorStatus.TOKEN_EXPIRED.getCode(),  ErrorStatus.TOKEN_EXPIRED.getMessage(), null);
    }

    /**
     * 현재 사용중인 JWT 검증 API
     */
    @GetMapping("/token/test")
    public String testJwtToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String email = tokenService.getUid(token);
        System.out.println(email);
        return email;
    }

    @GetMapping("/token/regenerate")
    public ApiResponse<TokenResponseDTO.TokenRefreshDTO> refreshAuth(HttpServletRequest request) {
        String token = request.getHeader("Refresh");
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            Token newToken = tokenService.generateToken(email, "USER");

            return ApiResponse.of(SuccessStatus.TOKEN_REFRESHED, tokenConverter.toTokenRefreshDTO(newToken));
        }

        throw new RuntimeException("유효한 refresh 토큰이 아닙니다.");
    }

}
