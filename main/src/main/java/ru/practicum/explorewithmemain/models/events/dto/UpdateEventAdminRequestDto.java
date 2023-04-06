package ru.practicum.explorewithmemain.models.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithmemain.models.events.Location;
import ru.practicum.explorewithmemain.utils.StateAction;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateEventAdminRequestDto {
    @Size(max = 1000, message = "Annotation cannot be longer than 1000 characters.")
    String annotation;

    Long category;

    @Size(max = 1000, message = "Description cannot be longer than 1000 characters.")
    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    @PositiveOrZero
    Long participantLimit;

    Boolean requestModeration;

    @Size(max = 1000, message = "Title cannot be longer than 1000 characters.")
    String title;

    StateAction stateAction;
}
