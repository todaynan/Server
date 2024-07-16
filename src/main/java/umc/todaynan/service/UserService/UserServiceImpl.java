package umc.todaynan.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.exception.handler.PreferCategoryHandler;
import umc.todaynan.apiPayload.exception.handler.UserHandler;
import umc.todaynan.converter.UserConverter;
import umc.todaynan.converter.UserPreferConverter;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.domain.entity.User.UserPrefer.PreferCategory;
import umc.todaynan.domain.entity.User.UserPrefer.UserPrefer;
import umc.todaynan.domain.enums.LoginType;
import umc.todaynan.oauth2.Token;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.oauth2.user.ProviderUser;
import umc.todaynan.repository.PreferCategoryRepository;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{


    private final PreferCategoryRepository preferCategoryRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserConverter userConverter;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    @Override
    public User join(String registrationId, ProviderUser providerUser){

        User user = User.builder()
                .loginType(LoginType.valueOf(registrationId))
                .nickName(providerUser.getUsername())
                .email(providerUser.getEmail())
                .build();

        //중복 회원 가입 방지
        if(userRepository.existsByEmail(user.getEmail())){
            return null;
        }
        else{
            return userRepository.save(user);
        }
    }
    @Transactional
    @Override
    public User joinUser(UserRequestDTO.JoinUserDTO joinUserDTO, String email, LoginType loginType) {
        User newUser = UserConverter.toUser(joinUserDTO, email, loginType);

        List<PreferCategory> preferCategoryList = joinUserDTO.getPreferCategory().stream()
                .map(category -> {
                    return preferCategoryRepository.findById(category).orElseThrow(() -> new PreferCategoryHandler(ErrorStatus.PREFER_CATEGORY_NOT_FOUND));
                }).collect(Collectors.toList());



        List<UserPrefer> userPreferList = UserPreferConverter.toUserPreferCategoryList(preferCategoryList);


        userPreferList.forEach(userPrefer -> {userPrefer.setUser(newUser);});

        logger.debug("PreferCategory list created: {}", newUser.getUserPreferList());


        if(userRepository.existsByEmail(newUser.getEmail())){
            return userRepository.findByEmail(newUser.getEmail()).orElseThrow(() -> new UserHandler(ErrorStatus.USER_ERROR));
        }
        else{
            return userRepository.save(newUser);
        }
    }

    @Override
    public Boolean verifyNickName(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @Override
    public UserResponseDTO.AutoLoginResultDTO autoLoginMember(HttpServletRequest httpServletRequest) {
        String givenToken = tokenService.getJwtFromHeader(httpServletRequest);
        String email = tokenService.getUid(givenToken);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserHandler(ErrorStatus.USER_ERROR));
        Token newToken = tokenService.generateToken(user.getEmail(), "USER");

        Date date = tokenService.getExpiration(newToken.getAccessToken());
        LocalDateTime expiration = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        return userConverter.toAutoLoginResultDTO(user, newToken,expiration);
    }

    @Override
    public UserResponseDTO.LoginResultDTO loginUser(String email) {
        if(userRepository.existsByEmail(email)) { //이미 존재
            Optional<User> user = userRepository.findByEmail(email);
            Token newToken = tokenService.generateToken(user.get().getEmail(), "USER");

            Date date = tokenService.getExpiration(newToken.getAccessToken());
            LocalDateTime expiration = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

            return userConverter.toLoginResultDTO(user.get(), newToken,expiration);
        }
        else{   //존재 X
            return null;
        }
    }
}
