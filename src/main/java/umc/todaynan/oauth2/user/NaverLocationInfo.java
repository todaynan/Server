package umc.todaynan.oauth2.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverLocationInfo {

    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<NaverLocationItems> items;
}
