package umc.todaynan.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.converter.UserConverter;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.oauth2.Token;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.oauth2.user.GoogleTokenInfo;
import umc.todaynan.oauth2.user.NaverTokenInfo;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.service.NaverService.NaverTokenService;
import umc.todaynan.service.GoogleService.GoogleTokenService;
import umc.todaynan.service.UserService.UserService;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    private final TokenService tokenService;
    private final UserService userService;
    private final UserConverter userConverter;
    private final GoogleTokenService googleTokenService;
    private final NaverTokenService naverTokenService;
    private final UserRepository userRepository;

    /**
     * 회원가입 API
     * Header : Social Access Token
     * Access Token -> Verify -> user create
     */
    @PostMapping("/signup")
    public ApiResponse<UserResponseDTO.JoinResultDTO> signUpUser(
            @RequestHeader("accessToken") String accessToken,
            @RequestParam("Login Type") LoginType loginType,
            @RequestBody UserRequestDTO.JoinUserDTO joinUserDTO) {

        switch (loginType) {
            case GOOGLE -> {
                Optional<GoogleTokenInfo> googleTokenInfo = googleTokenService.verifyAccessToken(accessToken);
                if (googleTokenInfo.isPresent()) {
                    if (userRepository.existsByEmail(googleTokenInfo.get().getEmail())) {
                        return ApiResponse.onFailure(ErrorStatus.USER_EXIST.getCode(), ErrorStatus.USER_EXIST.getMessage(), null);
                    }
                    User user = userService.joinUser(joinUserDTO, googleTokenInfo.get().getEmail(), loginType);
                    Token token = tokenService.generateToken(user.getEmail(), "USER");
                    return ApiResponse.of(SuccessStatus.USER_JOIN, userConverter.toJoinResultDTO(user, token));
                } else {
                    return ApiResponse.onFailure(ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getCode(), ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getMessage(), null);
                }
            }

            case NAVER -> {
                Optional<NaverTokenInfo> naverTokenInfo = naverTokenService.verifyAccessToken(accessToken);
                if (naverTokenInfo.isPresent()) {
                    if (userRepository.existsByEmail(naverTokenInfo.get().getResponse().getEmail())) {
                        return ApiResponse.onFailure(ErrorStatus.USER_EXIST.getCode(), ErrorStatus.USER_EXIST.getMessage(), null);
                    }
                    User user = userService.joinUser(joinUserDTO, naverTokenInfo.get().getResponse().getEmail(), loginType);
                    Token token = tokenService.generateToken(user.getEmail(), "USER");
                    return ApiResponse.of(SuccessStatus.USER_JOIN, userConverter.toJoinResultDTO(user, token));
                } else {
                    return ApiResponse.onFailure(ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getCode(), ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getMessage(), null);
                }
            }

            default -> {
                return ApiResponse.onFailure(ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getCode(), ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getMessage(), null);
            }
        }
    }
    /**
     * 닉네임 중복 확인 API
     * Path Variable : NickName
     * No Authentication
     */
    @GetMapping("/signup/{nickName}")
    public ApiResponse<String> verifyNickName(@PathVariable(name = "nickName") String nickName) {
        Boolean verify = userService.verifyNickName(nickName);
        if(verify) { //중복
            return ApiResponse.onFailure(ErrorStatus.USER_NICKNAME_EXIST.getCode(),
                    ErrorStatus.USER_NICKNAME_EXIST.getMessage(),  null);
        }else { //중복아님
            return ApiResponse.of(SuccessStatus.USER_NICKNAME_VERIFY, null);
        }
    }

    /**
     * 자동 로그인 API
     * Header : JWT Access Token
     * Authentication -> Security
     */
    @GetMapping("/auto-login/")
    public ApiResponse<UserResponseDTO.AutoLoginResultDTO> autoLogin(HttpServletRequest httpServletRequest) {
        return ApiResponse.of(SuccessStatus.USER_LOGIN, userService.autoLoginUser(httpServletRequest));
    }

    /**
     * 로그인 API
     * Path Variable : Social Access Token, Login Type
     * Authentication -> Security
     */
    @GetMapping("/login/")
    public ApiResponse<UserResponseDTO.LoginResultDTO> login(@RequestParam("Access Token") String accessToken,
                                                             @RequestParam("Login Type") LoginType loginType) {
        switch (loginType) {
            case GOOGLE -> {
                Optional<GoogleTokenInfo> googleTokenInfo = googleTokenService.verifyAccessToken(accessToken);
                if (googleTokenInfo.isPresent()) {
                    return ApiResponse.of(SuccessStatus.USER_LOGIN, userService.loginUser(googleTokenInfo.get().getEmail()));
                } else {
                    return ApiResponse.onFailure(ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getCode(), ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getMessage(), null);
                }
            }

            case NAVER-> {
                Optional<NaverTokenInfo> naverTokenInfo = naverTokenService.verifyAccessToken(accessToken);
                if (naverTokenInfo.isPresent()) {
                    return ApiResponse.of(SuccessStatus.USER_LOGIN, userService.loginUser(naverTokenInfo.get().getResponse().getEmail()));
                } else {
                    return ApiResponse.onFailure(ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getCode(), ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getMessage(), null);
                }
            }

            default -> {
                return ApiResponse.onFailure(ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getCode(), ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getMessage(), null);
            }
        }
    }
}
