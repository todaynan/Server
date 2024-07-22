package umc.todaynan.converter;

import org.springframework.stereotype.Component;
import umc.todaynan.domain.entity.User.UserPrefer.PreferCategory;
import umc.todaynan.domain.entity.User.UserPrefer.UserPrefer;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserPreferConverter {
    public static List<UserPrefer> toUserPreferCategoryList(List<PreferCategory> preferCategoryList) {
        return preferCategoryList.stream()
                .map(preferCategory ->
                        UserPrefer.builder()
                                .preferCategory(preferCategory)
                                .build()
                ).collect(Collectors.toList());
    }
}
