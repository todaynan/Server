package umc.todaynan.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import umc.todaynan.web.dto.SearchDTO.SearchPlaceDTO;
import umc.todaynan.web.controller.SearchRestController;
import umc.todaynan.web.dto.SearchDTO.SearchGeminiDTO;
import umc.todaynan.web.dto.SearchDTO.SearchImageDTO;
import umc.todaynan.web.dto.SearchDTO.SearchResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SearchConverter {

    private static final Logger logger = LoggerFactory.getLogger(SearchRestController.class);

    private final ObjectMapper objectMapper;

    public SearchConverter(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    public  SearchGeminiDTO.GeminiResponseDTO toGeminiSearchDTO(SearchGeminiDTO.GeminiResponseDTO geminiResponseDTO, List<SearchImageDTO.NaverImageItems> naverImageInfo) {
        List<SearchGeminiDTO.GeminiResponseItemDTO> searchResultDTOList = IntStream.range(0, geminiResponseDTO.getGeminiResponseItemDTOList().size())
                .mapToObj(i -> toGeminiSearchResponseDTO( geminiResponseDTO.getGeminiResponseItemDTOList().get(i),  naverImageInfo.get(i)))
                .collect(Collectors.toList());

        logger.debug("searchResultDTOList : {}", searchResultDTOList);

        return SearchGeminiDTO.GeminiResponseDTO.builder()
                .geminiResponseItemDTOList(searchResultDTOList)
                .build();
    }

    public  SearchGeminiDTO.GeminiResponseItemDTO toGeminiSearchResponseDTO(SearchGeminiDTO.GeminiResponseItemDTO geminiResponseItem, SearchImageDTO.NaverImageItems naverImageItem) {
        return SearchGeminiDTO.GeminiResponseItemDTO.builder()
                .title(geminiResponseItem.getTitle())
                .description(geminiResponseItem.getDescription())
                .image(naverImageItem.getThumbnail())
                .category(geminiResponseItem.getCategory())
                .build();
    }

    public SearchGeminiDTO.GeminiResponseDTO convertJsonToGeminiResponseDTO(String jsonString) throws JsonProcessingException {
        String validJsonString = jsonString.replaceAll("```json\\n|\\n```", "");

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, SearchGeminiDTO.GeminiResponseItemDTO.class);
        List<SearchGeminiDTO.GeminiResponseItemDTO> geminiResponseItemDTOList = objectMapper.readValue(validJsonString, listType);
        return SearchGeminiDTO.GeminiResponseDTO.builder().geminiResponseItemDTOList(geminiResponseItemDTOList).build();
    }

    public List<SearchPlaceDTO.GooglePlaceResultDTO> toGooglePlaceResponseDTO(JsonNode responseJson) {
        List<SearchPlaceDTO.GooglePlaceResultDTO> places = new ArrayList<>();
        for (JsonNode result : responseJson.get("places")) {
            String name = result.get("displayName").get("text").asText();
            String address = result.get("formattedAddress").asText();
            String type = result.get("types").get(0).asText();
            String photoUrl = result.get("photos") != null && result.get("photos").size() > 0 &&
                    result.get("photos").get(0).get("name") != null ?
                    result.get("photos").get(0).get("name").asText() : "";

            JsonNode viewportNode = responseJson.get("viewport");
            JsonNode northeastNode = viewportNode.get("low");
            JsonNode southwestNode = viewportNode.get("high");

            SearchPlaceDTO.GooglePlaceGeometryInfoDTO googlePlaceNorthDTO
                    = SearchPlaceDTO.GooglePlaceGeometryInfoDTO.builder()
                    .lat(northeastNode.get("latitude").asDouble())
                    .lng(northeastNode.get("longitude").asDouble())
                    .build();

            SearchPlaceDTO.GooglePlaceGeometryInfoDTO googlePlaceSouthDTO
                    = SearchPlaceDTO.GooglePlaceGeometryInfoDTO.builder()
                    .lat(southwestNode.get("latitude").asDouble())
                    .lng(southwestNode.get("longitude").asDouble())
                    .build();

            SearchPlaceDTO.GooglePlaceGeometryViewportDTO googlePlaceGeometryViewportDTO
                    = SearchPlaceDTO.GooglePlaceGeometryViewportDTO.builder()
                    .low(googlePlaceNorthDTO)
                    .high(googlePlaceSouthDTO)
                    .build();


            SearchPlaceDTO.GooglePlaceGeometryDTO googlePlaceGeometryDTO
                    = SearchPlaceDTO.GooglePlaceGeometryDTO.builder()
                    .viewport(googlePlaceGeometryViewportDTO)
                    .build();


            SearchPlaceDTO.GooglePlaceResultDTO googlePlaceResultDTO
                    = SearchPlaceDTO.GooglePlaceResultDTO.builder()
                    .name(name)
                    .photoUrl(photoUrl)
                    .geometry(googlePlaceGeometryDTO)
                    .type(type)
                    .address(address)
                    .build();

            places.add(googlePlaceResultDTO);
        }
        return places;
    }
}