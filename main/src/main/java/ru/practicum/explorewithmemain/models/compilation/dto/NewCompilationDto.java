package ru.practicum.explorewithmemain.models.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class NewCompilationDto {
    private List<Long> events;

    private Boolean pinned;

    @NotBlank
    private String title;
}