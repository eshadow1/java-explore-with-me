package ru.practicum.explorewithmemain.service.unknownuser.compilation;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.models.compilation.Compilation;
import ru.practicum.explorewithmemain.repository.compilation.CompilationRepository;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;
import ru.practicum.explorewithmemain.utils.exception.NotFoundException;

import java.util.List;

@Service
@Transactional
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

    @Override
    public List<Compilation> getAll(Boolean pinned, Integer from, Integer size) {
        var pageRequest = pinned == null
                ? new FromPageRequest(from, size, Sort.unsorted())
                : new FromPageRequest(from, size, Sort.by(Sort.Direction.DESC, "id"));

        return compilationRepository.findAllByPinned(pinned, pageRequest);
    }

    @Override
    public Compilation getCompilationById(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException(String.format("Set with id " + compId + " not found")));
    }
}
