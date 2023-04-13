package ru.practicum.explorewithmemain.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithmemain.models.request.Request;
import ru.practicum.explorewithmemain.models.request.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long userId);

    Optional<Request> findByRequesterIdAndEventId(Long userId, Long eventId);

    List<Request> findAllByIdInAndStatus(List<Long> requestIds, Status confirmed);
}