package ru.practicum.explorewithmemain.models.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithmemain.models.events.Location;
import ru.practicum.explorewithmemain.utils.ConstParameters;
import ru.practicum.explorewithmemain.utils.StateAction;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateEventRequestDto {
    @NotBlank
    @Size(max = 1000, message = "Annotation cannot be longer than 1000 characters.")
    private String annotation;

    private Long categoryId;

    @NotBlank
    @Size(max = 1000, message = "Description cannot be longer than 1000 characters.")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConstParameters.DATETIME_FORMATTER_WITH_SPACE)
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    private Boolean paid;

    private Long participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    @NotBlank
    @Size(max = 1000, message = "Title cannot be longer than 1000 characters.")
    private String title;
}
