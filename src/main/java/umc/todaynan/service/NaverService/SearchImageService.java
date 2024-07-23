package umc.todaynan.service.NaverService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import umc.todaynan.web.controller.SearchRestController;
import umc.todaynan.web.dto.SearchDTO.SearchImageDTO;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class SearchImageService {
    private static final String NAVER_IMAGE_URL = "https://openapi.naver.com/v1/search/image";

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    private static final Logger logger = LoggerFactory.getLogger(SearchRestController.class);

    public SearchImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<SearchImageDTO.NaverImageInfo> searchImage(String searchString) {
        try {
            searchString = searchString.replaceAll("<[^>]*>", "");
            URI url = UriComponentsBuilder.fromHttpUrl(NAVER_IMAGE_URL)
                    .queryParam("query", searchString)  // 제목을 검색 쿼리로 사용
                    .queryParam("display", 1)
                    .queryParam("start", 1)
                    .queryParam("sort", "sim")
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

            ResponseEntity<SearchImageDTO.NaverImageInfo> response = restTemplate.exchange(url, HttpMethod.GET, req, SearchImageDTO.NaverImageInfo.class);
            logger.debug("response body : {}", response.getBody().getItems());
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }
}
