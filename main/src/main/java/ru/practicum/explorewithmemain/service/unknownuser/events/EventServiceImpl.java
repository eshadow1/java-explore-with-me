package ru.practicum.explorewithmemain.service.unknownuser.events;

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
import ru.practicum.explorewithmemain.utils.model.SearchParameters;

import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllEvents(SearchParameters searchParameters,
                                    Integer from,
                                    Integer size) {
        if (searchParameters.getSort() == null) {
            searchParameters.setSort(SortType.DEFAULT);
        }

        Sort sortType;

        switch (searchParameters.getSort()) {
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
        if (searchParameters.getText() != null) {
            expression = expression.and(qEvent.annotation.containsIgnoreCase(searchParameters.getText()).or(qEvent.description.containsIgnoreCase(searchParameters.getText())));
        }

        if (searchParameters.getPaid() != null) {
            expression = expression.and(qEvent.paid.eq(searchParameters.getPaid()));
        }

        if (searchParameters.getCategories() != null && searchParameters.getCategories().size() > 0) {
            expression = expression.and(qEvent.category.id.in(searchParameters.getCategories()));
        }

        if (searchParameters.getRangeStart() != null) {
            expression = expression.and(qEvent.eventDate.goe(searchParameters.getRangeStart()));
        }

        if (searchParameters.getRangeEnd() != null) {
            expression = expression.and(qEvent.eventDate.loe(searchParameters.getRangeEnd()));
        }

        return eventRepository.findAll(expression, pageable).getContent();
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.findByIdAndState(id, State.PUBLISHED).orElseThrow(() -> new NotFoundException("Event not found"));
    }
}
