package ru.practicum.explorewithmemain.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.comment.dto.CommentDto;
import ru.practicum.explorewithmemain.models.comment.dto.UpdateCommentDto;
import ru.practicum.explorewithmemain.models.comment.mapper.CommentMapper;
import ru.practicum.explorewithmemain.service.admin.AdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/comments")
public class AdminCommentController {
    private final AdminService adminService;

    public AdminCommentController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<CommentDto> getComments(@RequestParam(required = false) List<Long> ids,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return adminService.getComments(ids, from, size).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable Long commentId) {
        return CommentMapper.toCommentDto(adminService.getComment(commentId));
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommentDto deleteComment(@PathVariable Long commentId) {
        return CommentMapper.toCommentDto(adminService.deleteComment(commentId));
    }

    @PatchMapping
    public CommentDto updateComment(@RequestBody @Valid UpdateCommentDto commentDto) {
        return CommentMapper.toCommentDto(adminService.updateComment(commentDto));
    }
}
