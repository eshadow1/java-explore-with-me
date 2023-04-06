package ru.practicum.explorewithmemain.service.admin;

import ru.practicum.explorewithmemain.models.compilation.Compilation;
import ru.practicum.explorewithmemain.models.compilation.dto.NewCompilationDto;

public interface AdminCompilationsService {
    Compilation addCompilation(NewCompilationDto newCompilationDto);

    Compilation removeCompilation(Long compilationId);

    Compilation pathCompilation(Long compilationId, NewCompilationDto newCompilationDto);
}
