package umc.todaynan.web.dto.SearchDTO;

import lombok.*;

import java.util.List;

public class SearchImageDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverImageInfo {
        private String lastBuildDate;
        private Integer total;
        private Integer start;
        private Integer display;
        private List<NaverImageItems> items;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NaverImageItems {
        private String title;
        private String link;
        private String thumbnail;
        private Long sizeheight;
        private Long sizewidth;
    }
}
