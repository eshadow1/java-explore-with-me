package ru.practicum.explorewithmemain.service.admin;

import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.dto.UpdateEventAdminRequestDto;
import ru.practicum.explorewithmemain.utils.model.SearchAdminParameters;

import java.util.List;

public interface AdminEventsService {
    List<Event> getEvents(SearchAdminParameters searchAdminParameters, Integer from, Integer size);

    Event patchEvent(Long eventId, UpdateEventAdminRequestDto updateEventAdminRequestDto);
}
