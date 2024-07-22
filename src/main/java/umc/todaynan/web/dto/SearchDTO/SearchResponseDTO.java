package umc.todaynan.web.dto.SearchDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SearchResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverSearchDTO {
        private List<NaverSearchResultDTO> naverSearchResultDTOList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverSearchResultDTO {
        private String title;
        private String category;
        private String description;
        private String roadAddress;
        private String thumbnail;
        private String mapx;
        private String mapy;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiSearchDTO {
        private List<GeminiSearchResultDTO> geminiSearchResultDTOList;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiSearchResultDTO {
        private String title;
        private String description;
    }
}
