package ru.practicum.explorewithmemain.service.user;

import ru.practicum.explorewithmemain.models.request.Request;

import java.util.List;

public interface UserRequestService {

    List<Request> getRequests(Long userId);

    Request addRequest(Long userId, Long eventId);

    Request cancelRequest(Long userId, Long requestId);
}
