package ru.practicum.explorewithmemain.repository.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithmemain.models.events.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
    Optional<Location> findByLatAndLon(Float lat, Float lon);
}
