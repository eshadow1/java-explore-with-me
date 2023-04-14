package ru.practicum.explorewithmemain.repository.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithmemain.models.comment.Comment;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByEventId(Long commentId, FromPageRequest pageRequest);

    Page<Comment> findByIdIn(List<Long> commentId, FromPageRequest pageRequest);
}
