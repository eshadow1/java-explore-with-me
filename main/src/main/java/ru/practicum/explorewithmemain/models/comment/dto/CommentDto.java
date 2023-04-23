package ru.practicum.explorewithmemain.models.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentDto {
    private Long id;

    private String text;

    private Long authorId;

    private Long eventId;

    private LocalDateTime createdOn;

    private LocalDateTime editedOn;
}
