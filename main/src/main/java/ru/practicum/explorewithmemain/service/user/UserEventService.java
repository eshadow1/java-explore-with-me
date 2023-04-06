package ru.practicum.explorewithmemain.service.user;

import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.dto.NewEventDto;
import ru.practicum.explorewithmemain.models.events.dto.UpdateEventRequestDto;
import ru.practicum.explorewithmemain.models.request.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithmemain.models.request.EventRequestStatusUpdateRequestResult;
import ru.practicum.explorewithmemain.models.request.Request;

import java.util.List;

public interface UserEventService {
    List<Event> getEvents(Long userId, Integer from, Integer size);

    Event addEvent(Long userId, NewEventDto newEventDto);

    Event getEvent(Long userId, Long eventId);

    Event patchEvent(Long userId, Long eventId, UpdateEventRequestDto updateEventRequestDto);

    List<Request> getRequestsByUser(Long userId, Long eventId);

    EventRequestStatusUpdateRequestResult changeStatusRequest(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
