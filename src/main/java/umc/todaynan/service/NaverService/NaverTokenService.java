package umc.todaynan.service.NaverService;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import umc.todaynan.web.dto.TokenDTO.TokenInfoDTO;

import java.util.Optional;

@Service
public class NaverTokenService {

    private static final String NAVER_TOKENINFO_URL = "https://openapi.naver.com/v1/nid/me";

    private final RestTemplate restTemplate;

    public NaverTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<TokenInfoDTO.NaverTokenInfo> verifyAccessToken(String accessToken) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(NAVER_TOKENINFO_URL)
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<TokenInfoDTO.NaverTokenInfo> response = restTemplate.exchange(url, HttpMethod.GET, entity, TokenInfoDTO.NaverTokenInfo.class);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return Optional.empty();  // Access Token이 유효하지 않은 경우 빈 Optional 반환
            }
            throw e;  // 다른 예외는 다시 던짐
        }
    }
}
