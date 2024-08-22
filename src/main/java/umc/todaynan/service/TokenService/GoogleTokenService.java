package umc.todaynan.service.TokenService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import umc.todaynan.web.dto.TokenDTO.TokenInfoDTO;

import java.util.Optional;

@Service
public class GoogleTokenService {

    private static final String GOOGLE_TOKENINFO_URL = "https://oauth2.googleapis.com/tokeninfo";

    private final RestTemplate restTemplate;

    public GoogleTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<TokenInfoDTO.GoogleTokenInfoDTO> verifyAccessToken(String accessToken) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(GOOGLE_TOKENINFO_URL)
                    .queryParam("access_token", accessToken)
                    .toUriString();

            TokenInfoDTO.GoogleTokenInfoDTO tokenInfo = restTemplate.getForObject(url, TokenInfoDTO.GoogleTokenInfoDTO.class);
            return Optional.ofNullable(tokenInfo);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return Optional.empty();  // Access Token이 유효하지 않은 경우 빈 Optional 반환
            }
            throw e;  // 다른 예외는 다시 던짐
        }
    }
}






