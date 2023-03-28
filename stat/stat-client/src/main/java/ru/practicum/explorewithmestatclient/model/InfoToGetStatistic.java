package ru.practicum.explorewithmestatclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class InfoToGetStatistic {
    @NotNull
    private String start;

    @NotNull
    private String end;

    private List<String> uris;

    private Boolean unique;

    private Long limit;

    private String application;
}
