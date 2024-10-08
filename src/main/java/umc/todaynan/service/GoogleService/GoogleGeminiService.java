package umc.todaynan.service.GoogleService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import umc.todaynan.web.dto.SearchDTO.SearchGeminiDTO;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class GoogleGeminiService {
    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";


    @Value("${GEMINI_API_KEY}")
    private String geminiApiKey;

    private final RestTemplate restTemplate;

    public GoogleGeminiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
    }

    public Optional<SearchGeminiDTO.GeminiSearchResultDTO> getGeminiSearch(List<String> userPreferTitleList) {
        try {
            URI url = UriComponentsBuilder.fromHttpUrl(GEMINI_URL)
                    .queryParam("key", geminiApiKey)
                    .build()
                    .toUri();

            String body = "{ \"contents\": [{ \"parts\": [{ \"text\": \"집에서 할 " + String.join(", ", userPreferTitleList) +" 3개 골구로 추천해주는데 형식은 json으로 title, description, category(놀이 유형) 이렇게 만들어줘.\" }] }] }";

            RequestEntity<String> req = RequestEntity
                    .post(url)
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(body);

            ResponseEntity<SearchGeminiDTO.GeminiSearchResultDTO> response = restTemplate.exchange(url, HttpMethod.POST, req, SearchGeminiDTO.GeminiSearchResultDTO.class);
            return Optional.ofNullable(response.getBody());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return Optional.empty();  // Access Token이 유효하지 않은 경우 빈 Optional 반환
            }
            throw e;  // 다른 예외는 다시 던짐
        }
    }
}
