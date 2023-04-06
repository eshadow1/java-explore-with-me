package ru.practicum.explorewithmemain.service.admin;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.models.category.Category;
import ru.practicum.explorewithmemain.models.compilation.Compilation;
import ru.practicum.explorewithmemain.models.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithmemain.models.compilation.mapper.CompilationMapper;
import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.QEvent;
import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.models.events.dto.UpdateEventAdminRequestDto;
import ru.practicum.explorewithmemain.repository.category.CategoryRepository;
import ru.practicum.explorewithmemain.repository.compilation.CompilationRepository;
import ru.practicum.explorewithmemain.repository.events.EventRepository;
import ru.practicum.explorewithmemain.utils.StateAction;
import ru.practicum.explorewithmemain.utils.exception.ConflictException;
import ru.practicum.explorewithmemain.utils.exception.NotFoundException;
import ru.practicum.explorewithmemain.models.user.User;
import ru.practicum.explorewithmemain.repository.user.UserRepository;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            CategoryRepository categoryRepository,
                            EventRepository eventRepository,
                            CompilationRepository compilationRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.eventRepository = eventRepository;
        this.compilationRepository = compilationRepository;
    }

    @Override
    public User addUser(User user) {
        checkNameUser(user);
        return userRepository.save(user);
    }


    @Override
    public List<User> getUsers(List<Long> usersId, Integer from, Integer size) {
        var pageRequest = new FromPageRequest(from, size, Sort.by(Sort.Direction.DESC, "id"));
        var result = usersId == null ? userRepository.findAll(pageRequest) : userRepository.findByIdIn(usersId, pageRequest);
        return result.stream().collect(Collectors.toList());
    }

    @Override
    public User removeUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id=" + userId + " was not found"));
        userRepository.deleteById(userId);
        return user;
    }

    @Override
    public Category addCategory(Category category) {
        checkNameCategory(category);
        return categoryRepository.save(category);
    }

    @Override
    public Category removeCategory(Long catId) {
        var category = categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException("Category with id=" + catId + " was not found"));
        checkEventsByCategory(category);
        categoryRepository.deleteById(catId);
        return category;
    }

    @Override
    public Category patchCategory(Category category) {
        checkNameCategory(category);

        var newCategory = categoryRepository.findById(category.getId()).orElseThrow(() -> new NotFoundException("Category not found with id=" + category.getId()));
        newCategory = newCategory.toBuilder()
                .name(category.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Event> getEvents(List<Long> users,
                                 List<State> states,
                                 List<Long> categories,
                                 LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd,
                                 Integer from,
                                 Integer size) {
        var pageRequest = new FromPageRequest(from, size, Sort.by(Sort.Direction.DESC, "id"));

        QEvent qEvent = QEvent.event;
        BooleanExpression expression = qEvent.id.isNotNull();

        if (users != null && users.size() > 0) {
            expression = expression.and(qEvent.initiator.id.in(users));
        }

        if (states != null && states.size() > 0) {
            expression = expression.and(qEvent.state.in(states));
        }

        if (categories != null && categories.size() > 0) {
            expression = expression.and(qEvent.category.id.in(categories));
        }

        if (rangeStart != null) {
            expression = expression.and(qEvent.eventDate.goe(rangeStart));
        }

        if (rangeEnd != null) {
            expression = expression.and(qEvent.eventDate.loe(rangeEnd));
        }

        return eventRepository.findAll(expression, pageRequest).getContent();
    }

    @Override
    public Event patchEvent(Long eventId, UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        var newEvent = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found with id=" + eventId));

        if (updateEventAdminRequestDto.getEventDate() != null
                && updateEventAdminRequestDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("The time for editing the event has expired");
        }
        if (StateAction.PUBLISH_EVENT.equals(updateEventAdminRequestDto.getStateAction())) {
            if (State.PUBLISHED.equals(newEvent.getState())
                    || State.CANCELED.equals(newEvent.getState())) {
                throw new ConflictException("Unable to publish this event");
            }
            newEvent.setState(State.PUBLISHED);
        }

        if (StateAction.REJECT_EVENT.equals(updateEventAdminRequestDto.getStateAction())) {
            if (State.PUBLISHED.equals(newEvent.getState())) {
                throw new ConflictException("Cannot cancel published event");
            }
            newEvent.setState(State.CANCELED);
        }

        if (updateEventAdminRequestDto.getTitle() != null
                && !updateEventAdminRequestDto.getTitle().isBlank()) {
            newEvent.setTitle(updateEventAdminRequestDto.getTitle());
        }

        if (updateEventAdminRequestDto.getAnnotation() != null
                && !updateEventAdminRequestDto.getAnnotation().isBlank()) {
            newEvent.setAnnotation(updateEventAdminRequestDto.getAnnotation());
        }

        if (updateEventAdminRequestDto.getDescription() != null
                && !updateEventAdminRequestDto.getDescription().isBlank()) {
            newEvent.setDescription(updateEventAdminRequestDto.getDescription());
        }

        if (updateEventAdminRequestDto.getEventDate() != null) {
            newEvent.setEventDate(updateEventAdminRequestDto.getEventDate());
        }

        if (updateEventAdminRequestDto.getPaid() != null) {
            newEvent.setPaid(updateEventAdminRequestDto.getPaid());
        }

        if (updateEventAdminRequestDto.getParticipantLimit() != null) {
            newEvent.setParticipantLimit(updateEventAdminRequestDto.getParticipantLimit());
        }

        if (updateEventAdminRequestDto.getRequestModeration() != null) {
            newEvent.setRequestModeration(updateEventAdminRequestDto.getRequestModeration());
        }

        if (updateEventAdminRequestDto.getCategory() != null) {
            Category category = categoryRepository.findById(updateEventAdminRequestDto.getCategory())
                    .orElseThrow(() -> new NotFoundException("Category not found"));
            newEvent.setCategory(category);
        }

        eventRepository.save(newEvent);
        return newEvent;
    }

    private void checkNameCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new ConflictException("This name is already taken");
        }
    }

    private void checkNameUser(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new ConflictException("This name is already taken");
        }
    }

    private void checkEventsByCategory(Category category) {
        if (eventRepository.countByCategoryId(category.getId()) > 0) {
            throw new ConflictException("This name is already taken");
        }
    }

    @Override
    public Compilation addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation;
        Set<Event> events;
        if (newCompilationDto.getEvents() == null || newCompilationDto.getEvents().isEmpty()) {
            events = Collections.emptySet();
        } else {
            events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
            if (newCompilationDto.getEvents().size() != events.size()) {
                throw new NotFoundException("No events found");
            }
        }

        compilation = CompilationMapper.fromNewCompilationDto(newCompilationDto, events);
        return compilationRepository.save(compilation);
    }

    @Override
    public Compilation removeCompilation(Long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId).orElseThrow(() ->
                new NotFoundException(String.format("Set with id" + compilationId + "not found")));
        compilationRepository.delete(compilation);
        return compilation;
    }

    @Override
    public Compilation pathCompilation(Long compilationId, NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationRepository.findById(compilationId).orElseThrow(() ->
                new NotFoundException(String.format("Set with id " + compilationId + " not found")));

        if (newCompilationDto.getTitle() != null && !newCompilationDto.getTitle().isBlank()) {
            compilation.setTitle(newCompilationDto.getTitle());
        }
        if (newCompilationDto.getPinned() != null) {
            compilation.setPinned(newCompilationDto.getPinned());
        }
        if (newCompilationDto.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllByIdIn(newCompilationDto.getEvents()));
        }

        return compilation;
    }
}
