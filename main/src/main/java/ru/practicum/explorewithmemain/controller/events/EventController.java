package ru.practicum.explorewithmemain.controller.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.events.dto.EventFullDto;
import ru.practicum.explorewithmemain.models.events.mapper.EventMapper;
import ru.practicum.explorewithmemain.service.events.EventService;
import ru.practicum.explorewithmemain.utils.SortType;
import ru.practicum.explorewithmestatclient.client.StatisticClient;
import ru.practicum.explorewithmestatdto.EndpointHitDto;


import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    @Value("${spring.application.name}")
    private String appName;

    private final StatisticClient statisticClient;

    public EventController(EventService eventService,
                           StatisticClient statisticClient) {
        this.eventService = eventService;
        this.statisticClient = statisticClient;

    }

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(required = false, name = "text") String text,
                                        @RequestParam(required = false, name = "categories") List<Long> categories,
                                        @RequestParam(required = false, name = "paid") Boolean paid,
                                        @RequestParam(required = false, name = "rangeStart") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                        @RequestParam(required = false, name = "rangeEnd") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                        @RequestParam(required = false, name = "onlyAvailable") Boolean onlyAvailable,
                                        @RequestParam(required = false, name = "sort") SortType sort,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                        HttpServletRequest httpRequest) {
        var events = eventService.getAllEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size);

        var endpointHitDto = EndpointHitDto.builder()
                .ip(httpRequest.getRemoteAddr())
                .app(appName)
                .uri(httpRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        try {
            statisticClient.addHits(endpointHitDto);
        } catch (RuntimeException error) {

        }
        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest httpRequest) {

        var event = eventService.getEvent(id);

        var endpointHitDto = EndpointHitDto.builder()
                .ip(httpRequest.getRemoteAddr())
                .app(appName)
                .uri(httpRequest.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        try {
            statisticClient.addHits(endpointHitDto);
        } catch (RuntimeException error) {

        }
        return EventMapper.toEventFullDto(event);
    }
}
