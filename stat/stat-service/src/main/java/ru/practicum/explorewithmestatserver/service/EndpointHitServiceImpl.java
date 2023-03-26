package ru.practicum.explorewithmestatserver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmestatserver.model.EndpointHit;
import ru.practicum.explorewithmestatserver.model.ViewStats;
import ru.practicum.explorewithmestatserver.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EndpointHitServiceImpl implements EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;

    public EndpointHitServiceImpl(EndpointHitRepository endpointHitRepository) {
        this.endpointHitRepository = endpointHitRepository;
    }

    @Override
    public EndpointHit addHit(EndpointHit endpointHit) {
        return endpointHitRepository.save(endpointHit);
    }

    @Override
    public List<ViewStats> getHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (unique) {
            return endpointHitRepository.getHitsUnique(start, end, uris);
        } else {
            return endpointHitRepository.getHits(start, end, uris);
        }
    }
}
