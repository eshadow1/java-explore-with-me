package ru.practicum.explorewithmemain.models.compilation.mapper;

import ru.practicum.explorewithmemain.models.compilation.Compilation;
import ru.practicum.explorewithmemain.models.compilation.dto.CompilationDto;
import ru.practicum.explorewithmemain.models.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.mapper.EventMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream().map(EventMapper::toEventShortDto).collect(Collectors.toList()))
                .build();
    }

    public static Compilation fromNewCompilationDto(NewCompilationDto newCompilationDto, Set<Event> events) {
        return Compilation.builder()
                .id(0L)
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .events(events)
                .build();
    }

    private CompilationMapper() {
    }
}
