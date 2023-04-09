package ru.practicum.explorewithmemain.models.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithmemain.models.request.Status;
import ru.practicum.explorewithmemain.utils.ConstParameters;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ParticipationRequestDto {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ConstParameters.DATETIME_FORMATTER_WITH_MILLISECONDS)
    private LocalDateTime created;

    private Long event;

    private Long requester;

    private Status status;
}
