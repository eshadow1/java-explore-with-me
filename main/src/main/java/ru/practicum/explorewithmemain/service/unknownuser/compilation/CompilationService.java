package ru.practicum.explorewithmemain.service.unknownuser.compilation;

import ru.practicum.explorewithmemain.models.compilation.Compilation;

import java.util.List;

public interface CompilationService {
    List<Compilation> getAll(Boolean pinned, Integer from, Integer size);

    Compilation getCompilationById(Long compId);
}
