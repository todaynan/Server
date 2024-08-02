package umc.todaynan.service.GoogleService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import umc.todaynan.converter.SearchConverter;
import umc.todaynan.web.dto.SearchDTO.SearchPlaceDTO;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GoogleSearchService {
    private static final String URL = "https://places.googleapis.com/v1/places:searchText";
    private static final String IMAGE_URL = "https://maps.googleapis.com/maps/api/place/photo";

    @Value("${GOOGLE_PLACE_KEY}")
    private String apiKey;
    private URI uri;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    private final SearchConverter searchConverter;

    public GoogleSearchService(RestTemplate restTemplate, SearchConverter searchConverter) {
        this.restTemplate = restTemplate;
        this.searchConverter = searchConverter;
    }

    public SearchPlaceDTO.GooglePlaceResponseDTO searchPlaces(String searchString, String pageToken) throws IOException {
        if (pageToken == null) {
            uri = UriComponentsBuilder.fromHttpUrl(URL)
                    .queryParam("pageSize", 5)
                    .queryParam("languageCode", "ko")
                    .encode(StandardCharsets.UTF_8)
                    .build()
                    .toUri();
        }else {
            uri = UriComponentsBuilder.fromHttpUrl(URL)
                    .queryParam("pageToken", pageToken)
                    .queryParam("pageSize", 5)
                    .queryParam("languageCode", "ko")
                    .encode(StandardCharsets.UTF_8)
                    .build()
                    .toUri();
        }

        String body = "{ \"textQuery\": \"" + searchString + "\" }";


        RequestEntity<String> req = RequestEntity
                .post(uri)
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("X-Goog-Api-Key", apiKey)
                .header("X-Goog-FieldMask", "places.displayName,places.formattedAddress,places.viewport,places.photos,places.types,nextPageToken")
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(req, String.class);
        JsonNode responseJson = objectMapper.readTree(response.getBody());

        List<SearchPlaceDTO.GooglePlaceResultDTO> googlePlaceResultDTOList = searchConverter.toGooglePlaceResponseDTO(responseJson);
        googlePlaceResultDTOList = addPhothUrl(googlePlaceResultDTOList);

        return SearchPlaceDTO.GooglePlaceResponseDTO.builder()
                .googlePlaceResultDTOList(googlePlaceResultDTOList)
                .pageToken(responseJson.get("nextPageToken").asText())
                .build();
    }

    public List<SearchPlaceDTO.GooglePlaceResultDTO> addPhothUrl(List<SearchPlaceDTO.GooglePlaceResultDTO> googlePlaceResultDTOList) {
        List<SearchPlaceDTO.GooglePlaceResultDTO> googlePlaceResultList = googlePlaceResultDTOList.stream().map( googlePlaceResult -> {
            String[] part = googlePlaceResult.getPhotoUrl().split("/");
            String photoUrl = String.format(IMAGE_URL+ "?maxwidth=400&photoreference=%s&key=%s",
                    part[part.length - 1],
                    apiKey);

            googlePlaceResult.setPhotoUrl(photoUrl);
            return googlePlaceResult;
        }).collect(Collectors.toList());
        return googlePlaceResultList;
    }
}
