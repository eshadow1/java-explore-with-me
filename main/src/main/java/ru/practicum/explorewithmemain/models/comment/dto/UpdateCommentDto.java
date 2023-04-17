package ru.practicum.explorewithmemain.models.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateCommentDto {

    @NotNull
    @PositiveOrZero
    private Long id;

    @NotBlank(message = "The text should not be blank")
    private String text;
}
