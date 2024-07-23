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
    private static final String URL = "https://maps.googleapis.com/maps/api/place/textsearch/json";
    private static final String IMAGE_URL = "https://maps.googleapis.com/maps/api/place/photo";

    @Value("${GOOGLE_PLACE_KEY}")
    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    private final SearchConverter searchConverter;

    public GoogleSearchService(RestTemplate restTemplate, SearchConverter searchConverter) {
        this.restTemplate = restTemplate;
        this.searchConverter = searchConverter;
    }

    public List<SearchPlaceDTO.GooglePlaceResultDTO> searchPlaces(String searchString) throws IOException {
        URI url = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("query", searchString)
                .queryParam("key", apiKey)
                .queryParam("language", "ko")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        RequestEntity<Void> req = RequestEntity
                .get(url)
                .header("Content-Type", "application/json; charset=UTF-8")
                .build();

        ResponseEntity<String> response = restTemplate.exchange(req, String.class);
        JsonNode responseJson = objectMapper.readTree(response.getBody());

        List<SearchPlaceDTO.GooglePlaceResultDTO> googlePlaceResultDTOList = searchConverter.toGooglePlaceResponseDTO(responseJson);
        googlePlaceResultDTOList = addPhothUrl(googlePlaceResultDTOList);

        return googlePlaceResultDTOList;
    }

    public List<SearchPlaceDTO.GooglePlaceResultDTO> addPhothUrl(List<SearchPlaceDTO.GooglePlaceResultDTO> googlePlaceResultDTOList) {
        List<SearchPlaceDTO.GooglePlaceResultDTO> googlePlaceResultList = googlePlaceResultDTOList.stream().map( googlePlaceResult -> {
            String photoUrl = String.format(IMAGE_URL+ "?maxwidth=400&photoreference=%s&key=%s",
                    googlePlaceResult.getPhotoUrl(),
                    apiKey);

            googlePlaceResult.setPhotoUrl(photoUrl);
            return googlePlaceResult;
        }).collect(Collectors.toList());
        return googlePlaceResultList;
    }
}
