package umc.todaynan.web.dto.SearchDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SearchGeminiDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiResponseDTO {
        private List<GeminiResponseItemDTO> geminiResponseItemDTOList;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiResponseItemDTO {
        private String title;
        private String description;
        private String category;
        private String image;
        private Boolean isLike;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiSearchResultDTO {
        private List<GeminiCandidatesDTO> candidates;
        private GeminiUsageMetadataDTO usageMetadata;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiCandidatesDTO {
        private GeminiContentDTO content;
        private String finishReason;
        private int index;
        private List<GeminiSafetyRatingDTO> safetyRatings;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiContentDTO {
        private List<GeminiPartDTO> parts;
        private String role;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiPartDTO {
        private String text;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiSafetyRatingDTO {
        private String category;
        private String probability;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeminiUsageMetadataDTO {
        private Integer promptTokenCount;
        private Integer candidatesTokenCount;
        private Integer totalTokenCount;
    }


}
