package ru.practicum.explorewithmemain.repository.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithmemain.models.compilation.Compilation;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByPinned(Boolean pinned, FromPageRequest pageRequest);
}
