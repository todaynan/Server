package umc.todaynan.web.dto.SearchDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SearchPlaceDTO {

    @Builder
    @Getter
    @Setter
    public static class GooglePlaceResponseDTO {
        private List<GooglePlaceResultDTO> googlePlaceResultDTOList;
        private String pageToken;
    }
    @Builder
    @Getter
    @Setter
    public static class GooglePlaceResultDTO {
        private GooglePlaceGeometryDTO geometry;
        private String name;
        private String address;
        private String photoUrl;
        private String type;
    }
    @Builder
    @Getter
    @Setter
    public static class GooglePlaceGeometryDTO {
        private GooglePlaceGeometryViewportDTO viewport;
    }
    @Builder
    @Getter
    @Setter
    public static class GooglePlaceGeometryViewportDTO {
        private GooglePlaceGeometryInfoDTO low;
        private GooglePlaceGeometryInfoDTO high;
    }
    @Builder
    @Getter
    @Setter
    public static class GooglePlaceGeometryInfoDTO {
        private Double lat;
        private Double lng;
    }

}



