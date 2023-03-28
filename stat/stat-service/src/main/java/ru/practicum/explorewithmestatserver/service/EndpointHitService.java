package ru.practicum.explorewithmestatserver.service;

import ru.practicum.explorewithmestatserver.model.EndpointHit;
import ru.practicum.explorewithmestatserver.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitService {
    EndpointHit addHit(EndpointHit endpointHit);

    List<ViewStats> getHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
