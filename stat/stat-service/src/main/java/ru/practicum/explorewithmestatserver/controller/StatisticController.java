package ru.practicum.explorewithmestatserver.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmestatdto.EndpointHitDto;
import ru.practicum.explorewithmestatdto.ViewStatsDto;
import ru.practicum.explorewithmestatdto.utils.ParamRest;
import ru.practicum.explorewithmestatserver.mapper.EndpointHitMapper;
import ru.practicum.explorewithmestatserver.mapper.ViewStatsMapper;
import ru.practicum.explorewithmestatserver.service.EndpointHitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class StatisticController {
    private final EndpointHitService endpointHitService;

    public StatisticController(EndpointHitService endpointHitService) {
        this.endpointHitService = endpointHitService;
    }

    @PostMapping(ParamRest.HITS_CONTROLLER)
    @ResponseStatus(HttpStatus.CREATED)
    public String addHits(@RequestBody @Valid EndpointHitDto endpointHit) {
        log.info("Получен запрос на добавление статистики: " + endpointHit);

        endpointHitService.addHit(EndpointHitMapper.fromEndpointHitDto(endpointHit));

        return "Информация сохранена";
    }

    @GetMapping(ParamRest.STATS_CONTROLLER)
    public List<ViewStatsDto> getStats(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam String[] uris,
                                       @RequestParam(defaultValue = "false") boolean unique) {
        log.info("Получен запрос на получение статистики c " + start + " до " + end);
        var test3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        var test = LocalDateTime.parse(start, test3);
        var test2 = LocalDateTime.parse(end, test3);
        return endpointHitService.getHits(test, test2, Arrays.asList(uris), unique).stream()
                .map(ViewStatsMapper::toViewStatsDto)
                .collect(Collectors.toList());
    }
}
