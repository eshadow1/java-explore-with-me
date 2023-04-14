package ru.practicum.explorewithmemain.models.comment.mapper;

import ru.practicum.explorewithmemain.models.comment.Comment;
import ru.practicum.explorewithmemain.models.comment.dto.CommentDto;

public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorId(comment.getAuthor().getId())
                .eventId(comment.getEvent().getId())
                .createdOn(comment.getCreatedOn())
                .editedOn(comment.getEditedOn())
                .build();
    }

    private CommentMapper() {}
}
