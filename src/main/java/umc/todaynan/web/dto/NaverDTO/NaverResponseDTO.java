package umc.todaynan.web.dto.NaverDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class NaverResponseDTO {
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
}
