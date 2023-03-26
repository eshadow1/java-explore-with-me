package ru.practicum.explorewithmestatserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ViewStats {
    private String app;

    private String uri;

    private Long hits;
}
