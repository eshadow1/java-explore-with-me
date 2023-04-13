package ru.practicum.explorewithmemain.models.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithmemain.models.category.dto.CategoryDto;
import ru.practicum.explorewithmemain.models.request.Request;
import ru.practicum.explorewithmemain.models.user.dto.UserShortDto;
import ru.practicum.explorewithmemain.utils.ConstParameters;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EventShortDto {
    private Long id;

    @Size(max = 1000, message = "Annotation cannot be longer than 1000 characters.")
    private String annotation;

    private CategoryDto categoryDto;

    private List<Request> confirmedRequests;

    @JsonFormat(pattern = ConstParameters.DATETIME_FORMATTER_WITH_SPACE)
    private LocalDateTime eventDate;

    private UserShortDto userShortDto;

    private Boolean paid;

    @Size(max = 1000, message = "Title cannot be longer than 1000 characters.")
    private String title;

    private Long views;
}
