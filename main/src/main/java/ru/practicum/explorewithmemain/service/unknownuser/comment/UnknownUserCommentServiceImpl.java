package ru.practicum.explorewithmemain.service.unknownuser.comment;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.models.comment.Comment;
import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.repository.comment.CommentRepository;
import ru.practicum.explorewithmemain.repository.events.EventRepository;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;
import ru.practicum.explorewithmemain.utils.exception.ConflictException;
import ru.practicum.explorewithmemain.utils.exception.NotFoundException;

import java.util.List;

@Service
@Transactional
public class UnknownUserCommentServiceImpl implements UnknownUserCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    public UnknownUserCommentServiceImpl(CommentRepository commentRepository,
                                         EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id " + commentId + " not found")));
    }

    @Override
    public List<Comment> getAllCommentsFromEvent(Long eventId, Integer from, Integer size) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event with id " + eventId + " not found"));

        if (event.getState() == State.PENDING) {
            throw new ConflictException("You can't get comments to not published events");
        }

        var pageRequest = new FromPageRequest(from, size, Sort.by(Sort.Direction.DESC, "id"));

        return commentRepository.findByEventId(event.getId(), pageRequest).getContent();
    }
}
