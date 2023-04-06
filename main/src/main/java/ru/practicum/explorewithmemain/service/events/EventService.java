package ru.practicum.explorewithmemain.service.events;

import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.utils.SortType;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents(String text,
                             List<Long> categories,
                             Boolean paid,
                             LocalDateTime rangeStart,
                             LocalDateTime rangeEnd,
                             Boolean onlyAvailable,
                             SortType sort,
                             Integer from,
                             Integer size);

    Event getEvent(Long id);
}
