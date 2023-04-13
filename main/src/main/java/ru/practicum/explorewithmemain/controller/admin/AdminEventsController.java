package ru.practicum.explorewithmemain.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.models.events.dto.EventFullDto;
import ru.practicum.explorewithmemain.models.events.dto.UpdateEventAdminRequestDto;
import ru.practicum.explorewithmemain.models.events.mapper.EventMapper;
import ru.practicum.explorewithmemain.service.admin.AdminService;
import ru.practicum.explorewithmemain.utils.ConstParameters;
import ru.practicum.explorewithmemain.utils.model.SearchAdminParameters;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/events")
public class AdminEventsController {
    private final AdminService adminService;

    public AdminEventsController(AdminService adminService) {
        this.adminService = adminService;

    }

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(required = false, name = "users") List<Long> users,
                                        @RequestParam(required = false, name = "states") List<State> states,
                                        @RequestParam(required = false, name = "categories") List<Long> categories,
                                        @DateTimeFormat(pattern = ConstParameters.DATETIME_FORMATTER_WITH_SPACE) @RequestParam(required = false, name = "rangeStart") LocalDateTime rangeStart,
                                        @DateTimeFormat(pattern = ConstParameters.DATETIME_FORMATTER_WITH_SPACE) @RequestParam(required = false, name = "rangeEnd") LocalDateTime rangeEnd,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        var searchAdminParameters = SearchAdminParameters.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .build();
        return adminService.getEvents(searchAdminParameters, from, size).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable Long eventId,
                                   @RequestBody UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        return EventMapper.toEventFullDto(adminService.patchEvent(eventId, updateEventAdminRequestDto));
    }
}
