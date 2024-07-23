package umc.todaynan.web.dto.SearchDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SearchPlaceDTO {
    @Builder
    @Getter
    @Setter
    public static class GooglePlaceResultDTO {
        private String placeId;
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
        private GooglePlaceGeometryInfoDTO location;
        private GooglePlaceGeometryViewportDTO viewport;
    }
    @Builder
    @Getter
    @Setter
    public static class GooglePlaceGeometryViewportDTO {
        private GooglePlaceGeometryInfoDTO northeast;
        private GooglePlaceGeometryInfoDTO southwest;
    }
    @Builder
    @Getter
    @Setter
    public static class GooglePlaceGeometryInfoDTO {
        private Double lat;
        private Double lng;
    }

}



