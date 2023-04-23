package ru.practicum.explorewithmemain.controller.unknownuser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.comment.dto.CommentDto;
import ru.practicum.explorewithmemain.models.comment.mapper.CommentMapper;
import ru.practicum.explorewithmemain.service.unknownuser.comment.UnknownUserCommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UnknownUserCommentController {
    private final UnknownUserCommentService unknownUserCommentService;

    public UnknownUserCommentController(UnknownUserCommentService unknownUserCommentService) {
        this.unknownUserCommentService = unknownUserCommentService;
    }

    @GetMapping(path = "/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAllComments(@PathVariable Long eventId,
                                           @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return unknownUserCommentService.getAllCommentsFromEvent(eventId, from, size).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable Long commentId) {
        return CommentMapper.toCommentDto(unknownUserCommentService.getComment(commentId));
    }
}
