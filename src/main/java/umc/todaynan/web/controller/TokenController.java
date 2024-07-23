package umc.todaynan.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.oauth2.TokenService;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

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
}
