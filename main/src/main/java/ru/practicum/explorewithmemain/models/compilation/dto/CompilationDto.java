package ru.practicum.explorewithmemain.models.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithmemain.models.events.dto.EventShortDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CompilationDto {
    private Long id;

    private List<EventShortDto> events;

    private Boolean pinned;

    private String title;
}
