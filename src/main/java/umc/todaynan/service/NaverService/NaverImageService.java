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
import umc.todaynan.oauth2.user.NaverImageInfo;
import umc.todaynan.oauth2.user.NaverLocationInfo;
import umc.todaynan.web.controller.NaverRestController;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class NaverImageService {
    private static final String NAVER_IMAGE_URL = "https://openapi.naver.com/v1/search/image";

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    private static final Logger logger = LoggerFactory.getLogger(NaverRestController.class);

    public NaverImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<NaverImageInfo> searchImage(String searchString) {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(NAVER_IMAGE_URL)
                    .queryParam("query", searchString)  // 제목을 검색 쿼리로 사용
                    .queryParam("display", 5)
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

            ResponseEntity<NaverImageInfo> response = restTemplate.exchange(url, HttpMethod.GET, req, NaverImageInfo.class);
            logger.debug("response body : {}", response.getBody().getItems());
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }
}
