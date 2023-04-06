package ru.practicum.explorewithmemain.controller.compilation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.compilation.dto.CompilationDto;
import ru.practicum.explorewithmemain.models.compilation.mapper.CompilationMapper;
import ru.practicum.explorewithmemain.service.compilation.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/compilations")
@Validated
public class CompilationController {
    private final CompilationService compilationService;

    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false, name = "pinned") Boolean pinned,
                                                @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return compilationService.getAll(pinned, from, size).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return CompilationMapper.toCompilationDto(compilationService.getCompilationById(compId));
    }
}