package ru.practicum.explorewithmemain.service.user;

import ru.practicum.explorewithmemain.models.comment.Comment;
import ru.practicum.explorewithmemain.models.comment.dto.NewCommentDto;

public interface UserCommentService {
    Comment addComment(Long eventId, Long userId, NewCommentDto newCommentDto);

    Comment deleteComment(Long userId, Long commentId, Long eventId);

    Comment updateComment(Long eventId, Long userId, Long commentId, NewCommentDto commentDto);

    Comment getComment(Long eventId, Long userId, Long commentId);
}
