package ru.practicum.explorewithmemain.repository.events;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.models.user.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    @Query("SELECT COUNT(e.id) " +
            "FROM Event AS e " +
            "WHERE e.category.id = ?1")
    Long countByCategoryId(Long id);

    Optional<Event> findByIdAndState(Long id, State published);

    List<Event> findAllByInitiator(User initiator, Pageable pageRequest);

    Set<Event> findAllByIdIn(List<Long> eventId);
}
