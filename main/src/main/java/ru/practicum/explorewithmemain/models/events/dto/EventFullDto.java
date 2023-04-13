package ru.practicum.explorewithmemain.models.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithmemain.models.category.dto.CategoryDto;
import ru.practicum.explorewithmemain.models.events.Location;
import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.models.request.Request;
import ru.practicum.explorewithmemain.models.user.dto.UserShortDto;
import ru.practicum.explorewithmemain.utils.ConstParameters;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EventFullDto {
    private Long id;

    @Size(max = 1000, message = "Annotation cannot be longer than 1000 characters.")
    private String annotation;

    private CategoryDto category;

    private List<Request> confirmedRequests;

    @JsonFormat(pattern = ConstParameters.DATETIME_FORMATTER_WITH_SPACE)
    private LocalDateTime createdOn;

    @JsonFormat(pattern = ConstParameters.DATETIME_FORMATTER_WITH_SPACE)
    private LocalDateTime eventDate;

    @Size(max = 1000, message = "Description cannot be longer than 1000 characters.")
    String description;

    private UserShortDto initiator;

    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;

    @JsonFormat(pattern = ConstParameters.DATETIME_FORMATTER_WITH_SPACE)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    @Size(max = 1000, message = "Title cannot be longer than 1000 characters.")
    private String title;

    private Long views;
}
