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
    String start;

    @NotNull
    String end;

    List<String> uris;

    Boolean unique;

    Long limit;

    String application;
}
