package ru.practicum.explorewithmemain.service.events;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.repository.events.EventRepository;
import ru.practicum.explorewithmemain.utils.SortType;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;
import ru.practicum.explorewithmemain.utils.exception.NotFoundException;
import ru.practicum.explorewithmemain.models.events.QEvent;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEvents(String text,
                                    List<Long> categories,
                                    Boolean paid,
                                    LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd,
                                    Boolean onlyAvailable,
                                    SortType sort,
                                    Integer from,
                                    Integer size) {
        if (sort == null) {
            sort = SortType.DEFAULT;
        }

        Sort sortType;

        switch (sort) {
            case EVENT_DATE:
                sortType = Sort.by(Sort.Direction.DESC, "eventDate");
                break;
            case VIEWS:
                sortType = Sort.by(Sort.Direction.DESC, "views");
                break;
            default:
                sortType = Sort.unsorted();
                break;
        }

        FromPageRequest pageable = new FromPageRequest(from, size, sortType);

        QEvent qEvent = QEvent.event;
        BooleanExpression expression = qEvent.state.eq(State.PUBLISHED);
        if (text != null) {
            expression = expression.and(qEvent.annotation.containsIgnoreCase(text).or(qEvent.description.containsIgnoreCase(text)));
        }

        if (paid != null) {
            expression = expression.and(qEvent.paid.eq(paid));
        }

        if (categories != null && categories.size() > 0) {
            expression = expression.and(qEvent.category.id.in(categories));
        }

        if (rangeStart != null) {
            expression = expression.and(qEvent.eventDate.goe(rangeStart));
        }

        if (rangeEnd != null) {
            expression = expression.and(qEvent.eventDate.loe(rangeEnd));
        }

        return eventRepository.findAll(expression, pageable).getContent();
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.findByIdAndState(id, State.PUBLISHED).orElseThrow(() -> new NotFoundException("Event not found"));
    }
}
