package ru.practicum.explorewithmemain.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.request.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithmemain.models.request.EventRequestStatusUpdateRequestResult;
import ru.practicum.explorewithmemain.models.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithmemain.models.request.mapper.RequestMapper;
import ru.practicum.explorewithmemain.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users/{userId}")
public class UserRequestController {
    private final UserService userService;

    public UserRequestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/requests")
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId) {
        return userService.getRequests(userId).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto postRequest(@PathVariable Long userId,
                                               @RequestParam(name = "eventId") Long eventId) {
        return RequestMapper.toParticipationRequestDto(userService.addRequest(userId, eventId));
    }

    @PatchMapping("/requests/{requestId}/cancel")
    public ParticipationRequestDto canselRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return RequestMapper.toParticipationRequestDto(userService.cancelRequest(userId, requestId));
    }

    @GetMapping("/events/{eventId}/requests")
    public List<ParticipationRequestDto> getUserRequestById(@PathVariable Long userId, @PathVariable Long eventId) {
        return userService.getRequestsByUser(userId, eventId).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/events/{eventId}/requests")
    public EventRequestStatusUpdateRequestResult patchUserRequestById(@PathVariable Long userId,
                                                                      @PathVariable Long eventId,
                                                                      @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return userService.changeStatusRequest(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
