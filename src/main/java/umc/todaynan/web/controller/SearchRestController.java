package umc.todaynan.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.converter.SearchConverter;
import umc.todaynan.converter.UserConverter;
import umc.todaynan.domain.entity.User.UserLike.UserLike;
import umc.todaynan.service.GoogleService.GeminiService;
import umc.todaynan.service.NaverService.SearchImageService;
import umc.todaynan.service.NaverService.SearchLocationService;
import umc.todaynan.service.UserService.UserService;
import umc.todaynan.web.dto.SearchDTO.SearchGeminiDTO;
import umc.todaynan.web.dto.SearchDTO.SearchImageDTO;
import umc.todaynan.web.dto.SearchDTO.SearchLocationDTO;
import umc.todaynan.web.dto.SearchDTO.SearchResponseDTO;
import umc.todaynan.web.dto.UserDTO.UserRequestDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class SearchRestController {

    private final SearchLocationService naverLocationService;
    private final SearchImageService naverImageService;
    private final GeminiService geminiService;
    private final SearchConverter naverConverter;
    private final UserService userService;
    private final UserConverter userConverter;
    private static final Logger logger = LoggerFactory.getLogger(SearchRestController.class);

    /**
     * 장소 검색 API
     * No Authorization
     */
    @GetMapping("/search/outside/{searchString}")
    public ApiResponse<SearchResponseDTO.NaverSearchDTO> getSearchLocation(
            HttpServletRequest httpServletRequest,
            @PathVariable(name = "searchString") String searchString) {
        Optional<SearchLocationDTO.NaverLocationInfo> naverLocationInfo = naverLocationService.searchLocation(searchString , httpServletRequest);
        if (naverLocationInfo.isPresent()) {
            List<SearchImageDTO.NaverImageItems> naverImageItemsList = naverLocationInfo.get().getItems().stream()
                    .map(locationItem -> {
                        Optional<SearchImageDTO.NaverImageInfo> naverImageInfo = naverImageService.searchImage(locationItem.getTitle());
                        if (naverImageInfo.isPresent()) {
                            return naverImageInfo.get().getItems().get(0);
                        } else {
                            return null;
                        }
                    }).collect(Collectors.toList());
            return ApiResponse.onSuccess(naverConverter.toSearchDTO(naverLocationInfo.get(), naverImageItemsList));
        } else {
            return ApiResponse.onFailure(ErrorStatus.SEARCH_ERROR.getCode(), ErrorStatus.SEARCH_ERROR.getMessage(), null);
        }
    }
    @GetMapping("/search/inside")
    public ApiResponse<SearchGeminiDTO.GeminiResponseDTO> getSearchInside(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        List<String> userPreferTitleList = userService.getPreferCategoryList(httpServletRequest);

        if (userPreferTitleList == null) {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }else {
            Optional<SearchGeminiDTO.GeminiSearchResultDTO> geminiSearchResultDTO
                    = geminiService.getGeminiSearch(userPreferTitleList);

            if(geminiSearchResultDTO.isPresent()) {
                logger.debug("geminiSearchResultDTO : {}" , geminiSearchResultDTO.get().getCandidates().get(0).getContent().getParts().get(0).getText());
                String result = geminiSearchResultDTO.get().getCandidates().get(0).getContent().getParts().get(0).getText();

                SearchGeminiDTO.GeminiResponseDTO geminiResponseDTO = geminiService.convertJsonToGeminiResponseDTO(result);

                List<SearchImageDTO.NaverImageItems> naverImageItemsList = geminiResponseDTO.getGeminiResponseItemDTOList().stream()
                        .map(item -> {
                            Optional<SearchImageDTO.NaverImageInfo> naverImageInfo = naverImageService.searchImage(item.getTitle());
                            if (naverImageInfo.isPresent()) {
                                return naverImageInfo.get().getItems().get(0);
                            } else {
                                return null;
                            }
                        }).collect(Collectors.toList());

                return ApiResponse.onSuccess(naverConverter.toGeminiSearchDTO(geminiResponseDTO, naverImageItemsList));
            }else {
                return ApiResponse.onFailure(ErrorStatus.SEARCH_ERROR.getCode(), ErrorStatus.SEARCH_ERROR.getMessage(), null);
            }
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

