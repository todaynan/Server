package umc.todaynan.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import umc.todaynan.oauth2.Token;
import umc.todaynan.oauth2.user.NaverImageInfo;
import umc.todaynan.oauth2.user.NaverImageItems;
import umc.todaynan.oauth2.user.NaverLocationInfo;
import umc.todaynan.oauth2.user.NaverLocationItems;
import umc.todaynan.web.controller.NaverRestController;
import umc.todaynan.web.dto.NaverDTO.NaverResponseDTO;
import umc.todaynan.web.dto.TokenDTO.TokenResponseDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class NaverConverter {

    private static final Logger logger = LoggerFactory.getLogger(NaverRestController.class);
    public  NaverResponseDTO.NaverSearchDTO toSearchDTO(NaverLocationInfo naverLocationInfo,NaverImageInfo naverImageInfo) {
        logger.debug("NaverLocationInfo : {}", naverLocationInfo.getItems());
        logger.debug("naverImageInfo : {}", naverImageInfo.getItems());

        List<NaverResponseDTO.NaverSearchResultDTO> searchResultDTOList = IntStream.range(0, naverLocationInfo.getItems().size())
                .mapToObj(i -> toSearchResultDTO( naverLocationInfo.getItems().get(i),  naverImageInfo.getItems().get(i)))
                .collect(Collectors.toList());

        logger.debug("searchResultDTOList : {}", searchResultDTOList);

        return NaverResponseDTO.NaverSearchDTO.builder()
                .naverSearchResultDTOList(searchResultDTOList)
                .build();
    }

    public  NaverResponseDTO.NaverSearchResultDTO toSearchResultDTO(NaverLocationItems naverLocationItem, NaverImageItems naverImageItem) {
        return NaverResponseDTO.NaverSearchResultDTO.builder()
                .title(naverLocationItem.getTitle())
                .category(naverLocationItem.getCategory())
                .description(naverLocationItem.getDescription())
                .roadAddress(naverLocationItem.getRoadAddress())
                .thumbnail(naverImageItem.getThumbnail())
                .mapx(naverLocationItem.getMapx())
                .mapy(naverLocationItem.getMapy())
                .build();
    }
}