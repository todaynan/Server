package umc.todaynan.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.converter.UserConverter;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.oauth2.Token;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.service.NaverService.NaverTokenService;
import umc.todaynan.service.GoogleService.GoogleTokenService;
import umc.todaynan.service.UserBlockingService.UserBlockingCommandService;
import umc.todaynan.service.UserPreferService.UserPreferCommandService;
import umc.todaynan.service.UserService.UserService;
import umc.todaynan.web.dto.TokenDTO.TokenInfoDTO;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserRestController {
    private final TokenService tokenService;
    private final UserService userService;
    private final UserConverter userConverter;
    private final GoogleTokenService googleTokenService;
    private final NaverTokenService naverTokenService;
    private final UserPreferCommandService userPreferCommandService;
    private final UserBlockingCommandService userBlockingCommandService;

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
                Optional<TokenInfoDTO.GoogleTokenInfo> googleTokenInfo = googleTokenService.verifyAccessToken(accessToken);
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
                Optional<TokenInfoDTO.NaverTokenInfo> naverTokenInfo = naverTokenService.verifyAccessToken(accessToken);
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
                Optional<TokenInfoDTO.GoogleTokenInfo> googleTokenInfo = googleTokenService.verifyAccessToken(accessToken);
                if (googleTokenInfo.isPresent()) {
                    return ApiResponse.of(SuccessStatus.USER_LOGIN, userService.loginUser(googleTokenInfo.get().getEmail()));
                } else {
                    return ApiResponse.onFailure(ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getCode(), ErrorStatus.USER_ACCESS_TOKEN_NOT_VERITY.getMessage(), null);
                }
            }

            case NAVER-> {
                Optional<TokenInfoDTO.NaverTokenInfo> naverTokenInfo = naverTokenService.verifyAccessToken(accessToken);
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

    @PatchMapping("/nickname")
    public ApiResponse<UserResponseDTO.UserModifyDTO> changeUserNickName(HttpServletRequest request, @RequestBody String Nickname) {
        String givenToken = tokenService.getJwtFromHeader(request);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("[UserController - changeUserNickName]{}번 유저의 닉네임을 {}로 수정합니다.", user.get().getId(), Nickname);
            userService.changeNickNameByUserId(user.get().getId(), Nickname);
            UserResponseDTO.UserModifyDTO responseDto = new UserResponseDTO.UserModifyDTO();
            responseDto.setMessage("닉네임 수정 완료");
            return ApiResponse.onSuccess(responseDto);
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }
    }

    @PatchMapping("/address")
    public ApiResponse<UserResponseDTO.UserModifyDTO> changeUserAddress(HttpServletRequest request, @RequestBody String MyTown) {
        String givenToken = tokenService.getJwtFromHeader(request);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("[UserController - changeUserAddress] {}번 유저의 주소를 {}로 수정합니다", user.get().getId(), MyTown);
            userService.changeMyAddress(user.get().getId(), MyTown);
            UserResponseDTO.UserModifyDTO responseDto = new UserResponseDTO.UserModifyDTO();
            responseDto.setMessage("동네 변경 완료");
            return ApiResponse.onSuccess(responseDto);
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }

    }

    @PatchMapping("/interest")
    public ApiResponse<UserResponseDTO.UserModifyDTO> changeInterest(HttpServletRequest request, @RequestBody List<String> prefer_list) {
        String givenToken = tokenService.getJwtFromHeader(request);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("[UserController - changeInterest] {}번 유저의 관심사를 {}로 수정합니다", user.get().getId(), prefer_list);
            userPreferCommandService.changeMyInterset(user.get().getId(), prefer_list);
            UserResponseDTO.UserModifyDTO responseDto = new UserResponseDTO.UserModifyDTO();
            responseDto.setMessage("관심사 수정 완료");
            return ApiResponse.onSuccess(responseDto);
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }
    }

    @DeleteMapping("/signout")
    public ApiResponse<UserResponseDTO.UserModifyDTO> signOut(HttpServletRequest request) {
        String givenToken = tokenService.getJwtFromHeader(request);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("[UserController - signOut] {}번 유저의 탈퇴입니다.", user.get().getId());
            userService.userSignOut(user.get().getId());
            UserResponseDTO.UserModifyDTO responseDto = new UserResponseDTO.UserModifyDTO();
            responseDto.setMessage("유저 탈퇴 완료");
            return ApiResponse.onSuccess(responseDto);
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }

    }

    @PostMapping("/block")
    public ApiResponse<UserResponseDTO.UserModifyDTO> userBlock(HttpServletRequest request, @RequestBody String BlockUserNickName) {
        String givenToken = tokenService.getJwtFromHeader(request);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("[UserController - userBlock] {}번 유저가 {} 유저를 차단합니다.", user.get().getId(), BlockUserNickName);
            userBlockingCommandService.user1BlockUser2ByUserId(user.get().getId(), BlockUserNickName);
            UserResponseDTO.UserModifyDTO responseDto = new UserResponseDTO.UserModifyDTO();
            responseDto.setMessage("차단 완료");
            return ApiResponse.onSuccess(responseDto);
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }
    }
}
