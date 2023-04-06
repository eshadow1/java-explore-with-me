package ru.practicum.explorewithmemain.service.admin;

import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.dto.UpdateEventAdminRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventsService {
    List<Event> getEvents(List<Long> users, List<State> states, List<Long> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    Event patchEvent(Long eventId, UpdateEventAdminRequestDto updateEventAdminRequestDto);
}
