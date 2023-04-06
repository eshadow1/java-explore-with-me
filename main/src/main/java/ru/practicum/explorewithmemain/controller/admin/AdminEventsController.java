package ru.practicum.explorewithmemain.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.models.events.dto.EventFullDto;
import ru.practicum.explorewithmemain.models.events.dto.UpdateEventAdminRequestDto;
import ru.practicum.explorewithmemain.models.events.mapper.EventMapper;
import ru.practicum.explorewithmemain.service.admin.AdminService;

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
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  @RequestParam(required = false, name = "rangeStart") LocalDateTime rangeStart,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  @RequestParam(required = false, name = "rangeEnd") LocalDateTime rangeEnd,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return adminService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable Long eventId,
                                   @RequestBody UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        return EventMapper.toEventFullDto(adminService.patchEvent(eventId, updateEventAdminRequestDto));
    }
}
