package ru.practicum.explorewithmemain.service.unknownuser.events;

import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.utils.model.SearchParameters;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents(SearchParameters searchParameters,
                             Integer from,
                             Integer size);

    Event getEvent(Long id);
}
