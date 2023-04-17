package ru.practicum.explorewithmemain.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.comment.dto.CommentDto;
import ru.practicum.explorewithmemain.models.comment.dto.NewCommentDto;
import ru.practicum.explorewithmemain.models.comment.dto.UpdateCommentDto;
import ru.practicum.explorewithmemain.models.comment.mapper.CommentMapper;
import ru.practicum.explorewithmemain.service.user.UserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
public class UserCommentController {
    private final UserService userService;

    public UserCommentController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable Long userId,
                                 @PathVariable Long eventId,
                                 @PathVariable Long commentId) {
        return CommentMapper.toCommentDto(userService.getComment(eventId, userId, commentId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto postComment(@PathVariable Long eventId,
                                  @PathVariable Long userId,
                                  @RequestBody @Valid NewCommentDto newCommentDto) {
        return CommentMapper.toCommentDto(userService.addComment(eventId, userId, newCommentDto));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommentDto deleteComment(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    @PathVariable Long commentId) {
        return CommentMapper.toCommentDto(userService.deleteComment(userId, commentId, eventId));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    @RequestBody @Valid UpdateCommentDto commentDto) {
        return CommentMapper.toCommentDto(userService.updateComment(eventId, userId, commentDto));
    }
}
