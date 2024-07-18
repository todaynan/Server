package umc.todaynan.web.controller;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.converter.NaverConverter;
import umc.todaynan.converter.UserConverter;
import umc.todaynan.domain.entity.User.UserLike.UserLike;
import umc.todaynan.domain.enums.PlaceCategory;
import umc.todaynan.oauth2.user.GoogleTokenInfo;
import umc.todaynan.oauth2.user.NaverImageInfo;
import umc.todaynan.oauth2.user.NaverLocationInfo;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.service.NaverService.NaverImageService;
import umc.todaynan.service.NaverService.NaverLocationService;
import umc.todaynan.service.UserService.UserService;
import umc.todaynan.web.dto.NaverDTO.NaverResponseDTO;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class NaverRestController {

    private final NaverLocationService naverLocationService;
    private final NaverImageService naverImageService;
    private final NaverConverter naverConverter;
    private final UserService userService;
    private final UserConverter userConverter;

    /**
     * 장소 검색 API
     * No Authorization
     */
    @GetMapping("/search/outside//{searchString}")
    public ApiResponse<NaverResponseDTO.NaverSearchDTO> getSearchLocation(
            @PathVariable(name = "searchString") String searchString) {
        Optional<NaverLocationInfo> naverLocationInfo = naverLocationService.searchLocation(searchString);
        if (naverLocationInfo.isPresent()) {
            Optional<NaverImageInfo> naverImageInfo = naverImageService.searchImage(searchString);
            if (naverImageInfo.isPresent()) {
                return ApiResponse.onSuccess(naverConverter.toSearchDTO(naverLocationInfo.get(), naverImageInfo.get()));
            } else {
                return ApiResponse.onFailure(ErrorStatus.SEARCH_LOCATION_ERROR.getCode(), ErrorStatus.SEARCH_LOCATION_ERROR.getMessage(), null);
            }
        } else {
            return ApiResponse.onFailure(ErrorStatus.SEARCH_LOCATION_ERROR.getCode(), ErrorStatus.SEARCH_LOCATION_ERROR.getMessage(), null);
        }
    }

    /**
     * 장소 좋아요 API
     * Authorization -> JWT
     */
    @PostMapping("/like")
    public ApiResponse<UserResponseDTO.UserLikeResultDTO> postLikeLocation(
            HttpServletRequest httpServletRequest,
            @RequestBody UserRequestDTO.UserLikeDTO userLikeDTO) {

        UserLike userLike = userService.likeLocation(httpServletRequest, userLikeDTO);

        if (userLike == null) {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }else {
            return ApiResponse.onSuccess(userConverter.toUserLikeResultDTO(userLike));
        }
    }

    /**
     * 장소 좋아요 모아보기 API
     * Authorization -> JWT
     */
    @GetMapping("/like")
    public ApiResponse<UserResponseDTO.GetUserLikeListResultDTO> getLikeLocationList(
            HttpServletRequest httpServletRequest) {

        UserResponseDTO.GetUserLikeListResultDTO userLikeListResultList = userService.likeLocationList(httpServletRequest);

        if (userLikeListResultList == null) {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }else {
            return ApiResponse.onSuccess(userLikeListResultList);
        }
    }
}

