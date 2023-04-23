package ru.practicum.explorewithmemain.service.unknownuser.comment;

import ru.practicum.explorewithmemain.models.comment.Comment;

import java.util.List;

public interface UnknownUserCommentService {
    Comment getComment(Long commentId);

    List<Comment> getAllCommentsFromEvent(Long eventId, Integer from, Integer size);
}
