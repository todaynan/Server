package umc.todaynan.web.dto.SearchDTO;

import lombok.*;

import java.util.List;

public class SearchLocationDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverLocationInfo {

        private String lastBuildDate;
        private Integer total;
        private Integer start;
        private Integer display;
        private List<NaverLocationItems> items;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverLocationItems {
        private String title ="";
        private String link ="";
        private String category ="";
        private String description = "";
        private String telephone ="";
        private String address ="";
        private String roadAddress ="";
        private String mapx ="";
        private String mapy ="";
    }
}
