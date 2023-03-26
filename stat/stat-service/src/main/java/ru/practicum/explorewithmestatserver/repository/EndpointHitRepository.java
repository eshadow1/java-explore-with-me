package ru.practicum.explorewithmestatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithmestatserver.model.EndpointHit;
import ru.practicum.explorewithmestatserver.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {

    @Query("SELECT new ru.practicum.explorewithmestatserver.model.ViewStats(endpoint.app, endpoint.uri, count(endpoint.ip)) " +
            "FROM EndpointHit as endpoint " +
            "WHERE endpoint.timestamp between ?1 and ?2 and endpoint.uri in ?3 " +
            "GROUP BY endpoint.app, endpoint.uri " +
            "order by count(endpoint.ip) DESC")
    List<ViewStats> getHits(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.explorewithmestatserver.model.ViewStats(endpoint.app, endpoint.uri, count(distinct endpoint.ip)) " +
            "FROM EndpointHit as endpoint " +
            "WHERE endpoint.timestamp between ?1 and ?2 and endpoint.uri in ?3 " +
            "GROUP BY endpoint.app, endpoint.uri " +
            "order by count(endpoint.ip) DESC")
    List<ViewStats> getHitsUnique(LocalDateTime start, LocalDateTime end, List<String> uris);
}
