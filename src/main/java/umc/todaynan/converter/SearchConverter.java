package umc.todaynan.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import umc.todaynan.web.controller.SearchRestController;
import umc.todaynan.web.dto.SearchDTO.SearchGeminiDTO;
import umc.todaynan.web.dto.SearchDTO.SearchImageDTO;
import umc.todaynan.web.dto.SearchDTO.SearchLocationDTO;
import umc.todaynan.web.dto.SearchDTO.SearchResponseDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SearchConverter {

    private static final Logger logger = LoggerFactory.getLogger(SearchRestController.class);
    public  SearchResponseDTO.NaverSearchDTO toSearchDTO(SearchLocationDTO.NaverLocationInfo naverLocationInfo, List<SearchImageDTO.NaverImageItems> naverImageInfo) {
        logger.debug("NaverLocationInfo : {}", naverLocationInfo.getItems());
        logger.debug("naverImageInfo : {}", naverImageInfo);

        List<SearchResponseDTO.NaverSearchResultDTO> searchResultDTOList = IntStream.range(0, naverLocationInfo.getItems().size())
                .mapToObj(i -> toSearchResultDTO( naverLocationInfo.getItems().get(i),  naverImageInfo.get(i)))
                .collect(Collectors.toList());

        logger.debug("searchResultDTOList : {}", searchResultDTOList);

        return SearchResponseDTO.NaverSearchDTO.builder()
                .naverSearchResultDTOList(searchResultDTOList)
                .build();
    }

    public  SearchResponseDTO.NaverSearchResultDTO toSearchResultDTO(SearchLocationDTO.NaverLocationItems naverLocationItem, SearchImageDTO.NaverImageItems naverImageItem) {
        return SearchResponseDTO.NaverSearchResultDTO.builder()
                .title(naverLocationItem.getTitle())
                .category(naverLocationItem.getCategory())
                .description(naverLocationItem.getDescription())
                .roadAddress(naverLocationItem.getRoadAddress())
                .thumbnail(naverImageItem.getThumbnail())
                .mapx(naverLocationItem.getMapx())
                .mapy(naverLocationItem.getMapy())
                .build();
    }

    public  SearchGeminiDTO.GeminiResponseDTO toGeminiSearchDTO(SearchGeminiDTO.GeminiResponseDTO geminiResponseDTO, List<SearchImageDTO.NaverImageItems> naverImageInfo) {
        List<SearchGeminiDTO.GeminiResponseItemDTO> searchResultDTOList = IntStream.range(0, geminiResponseDTO.getGeminiResponseItemDTOList().size())
                .mapToObj(i -> toGeminiSearchResultDTO( geminiResponseDTO.getGeminiResponseItemDTOList().get(i),  naverImageInfo.get(i)))
                .collect(Collectors.toList());

        logger.debug("searchResultDTOList : {}", searchResultDTOList);

        return SearchGeminiDTO.GeminiResponseDTO.builder()
                .geminiResponseItemDTOList(searchResultDTOList)
                .build();
    }

    public  SearchGeminiDTO.GeminiResponseItemDTO toGeminiSearchResultDTO(SearchGeminiDTO.GeminiResponseItemDTO geminiResponseItem, SearchImageDTO.NaverImageItems naverImageItem) {
        return SearchGeminiDTO.GeminiResponseItemDTO.builder()
                .title(geminiResponseItem.getTitle())
                .description(geminiResponseItem.getDescription())
                .image(naverImageItem.getThumbnail())
                .category(geminiResponseItem.getCategory())
                .build();
    }
}