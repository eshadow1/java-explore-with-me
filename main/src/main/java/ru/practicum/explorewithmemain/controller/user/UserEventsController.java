package ru.practicum.explorewithmemain.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.events.dto.EventFullDto;
import ru.practicum.explorewithmemain.models.events.dto.NewEventDto;
import ru.practicum.explorewithmemain.models.events.dto.UpdateEventRequestDto;
import ru.practicum.explorewithmemain.models.events.mapper.EventMapper;
import ru.practicum.explorewithmemain.service.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
public class UserEventsController {
    private final UserService userService;

    public UserEventsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<EventFullDto> getUserEvents(@PathVariable Long userId,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.getEvents(userId, from, size).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto postUserEvents(@PathVariable Long userId, @RequestBody @Valid NewEventDto newEventDto) {
        return EventMapper.toEventFullDto(userService.addEvent(userId, newEventDto));
    }

    @GetMapping("/{eventId}")
    public EventFullDto getUserEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return EventMapper.toEventFullDto(userService.getEvent(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchUserEventById(@PathVariable Long userId, @PathVariable Long eventId, @RequestBody UpdateEventRequestDto updateEventRequestDto) {
        return EventMapper.toEventFullDto(userService.patchEvent(userId, eventId, updateEventRequestDto));
    }
}
