package ru.practicum.explorewithmestatdto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ViewStatsDto {
    private String app;

    private String uri;

    private Long hits;
}
