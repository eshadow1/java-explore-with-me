package ru.practicum.explorewithmemain.service.admin;

import ru.practicum.explorewithmemain.models.comment.Comment;
import ru.practicum.explorewithmemain.models.comment.dto.UpdateCommentDto;

import java.util.List;

public interface AdminCommentService {
    Comment getComment(Long commentId);

    Comment updateComment(UpdateCommentDto commentDto);

    Comment deleteComment(Long commentId);

    List<Comment> getComments(List<Long> ids, Integer from, Integer size);
}
