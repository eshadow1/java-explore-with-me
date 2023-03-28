package ru.practicum.explorewithmestatserver.mapper;

import ru.practicum.explorewithmestatdto.ViewStatsDto;
import ru.practicum.explorewithmestatserver.model.ViewStats;

public class ViewStatsMapper {
    public static ViewStatsDto toViewStatsDto(final ViewStats viewStats) {
        return ViewStatsDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }

    private ViewStatsMapper() {
    }
}
