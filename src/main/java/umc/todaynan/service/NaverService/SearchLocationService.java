package umc.todaynan.service.NaverService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.web.dto.SearchDTO.SearchLocationDTO;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class SearchLocationService {

    private static final String NAVER_LOCATION_URL = "https://openapi.naver.com/v1/search/local.json";

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    public SearchLocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<SearchLocationDTO.NaverLocationInfo> searchLocation(String searchString, HttpServletRequest httpServletRequest) {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(NAVER_LOCATION_URL)
                    .queryParam("query", searchString)
                    .queryParam("display", 3)
                    .queryParam("start", 1)
                    .encode(Charset.forName("UTF-8"))
                    .build()
                    .toUri();

            // 검색 API 요청 생성
            RequestEntity<Void> req = RequestEntity
                    .get(url)
                    .header("X-Naver-Client-Id", naverClientId)
                    .header("X-Naver-Client-Secret", naverClientSecret)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .build();

            ResponseEntity<SearchLocationDTO.NaverLocationInfo> response = restTemplate.exchange(req, SearchLocationDTO.NaverLocationInfo.class);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }
}
