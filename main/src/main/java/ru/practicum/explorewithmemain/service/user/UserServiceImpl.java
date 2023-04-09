package ru.practicum.explorewithmemain.service.user;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.State;
import ru.practicum.explorewithmemain.models.events.dto.NewEventDto;
import ru.practicum.explorewithmemain.models.events.dto.UpdateEventRequestDto;
import ru.practicum.explorewithmemain.models.request.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithmemain.models.request.EventRequestStatusUpdateRequestResult;
import ru.practicum.explorewithmemain.models.request.Request;
import ru.practicum.explorewithmemain.models.request.Status;
import ru.practicum.explorewithmemain.models.request.mapper.RequestMapper;
import ru.practicum.explorewithmemain.repository.category.CategoryRepository;
import ru.practicum.explorewithmemain.repository.events.EventRepository;
import ru.practicum.explorewithmemain.repository.events.LocationRepository;
import ru.practicum.explorewithmemain.repository.request.RequestRepository;
import ru.practicum.explorewithmemain.repository.user.UserRepository;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;
import ru.practicum.explorewithmemain.utils.exception.ConflictException;
import ru.practicum.explorewithmemain.utils.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final LocationRepository locationRepository;

    public UserServiceImpl(UserRepository userRepository,
                           EventRepository eventRepository,
                           CategoryRepository categoryRepository,
                           RequestRepository requestRepository,
                           LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Event> getEvents(Long userId, Integer from, Integer size) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));

        var pageRequest = new FromPageRequest(from, size, Sort.by(Sort.Direction.DESC, "id"));
        return eventRepository.findAllByInitiator(user, pageRequest);
    }

    @Override
    public Event addEvent(Long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate() != null
                && newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("должно содержать дату, которая еще не наступила");
        }
        var initiator = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));
        var category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        var location = locationRepository.findByLatAndLon(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon()).orElse(null);

        if (location == null) {
            location = newEventDto.getLocation().toBuilder()
                    .id(0L)
                    .build();
            location = locationRepository.save(location);
        }

        var event = Event.builder()
                .id(0L)
                .category(category)
                .initiator(initiator)
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .location(location)
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .annotation(newEventDto.getAnnotation())
                .createdOn(LocalDateTime.now())
                .requests(new ArrayList<>())
                .publishedOn(null)
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .state(State.PENDING)
                .views(0L)
                .build();
        return eventRepository.save(event);
    }

    @Override
    public Event getEvent(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));

        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                "Category with eventId:" + eventId + "not found"));
        if (!userId.equals(event.getInitiator().getId())) {
            throw new NotFoundException("user with id:" + userId + " not found in event");
        }

        return event;
    }

    @Override
    public Event patchEvent(Long userId, Long eventId, UpdateEventRequestDto updateEventRequestDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));

        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                "Category with eventId:" + eventId + "not found"));

        if (State.PUBLISHED.equals(event.getState())) {
            throw new ConflictException("You cannot edit published events");
        }

        if (!userId.equals(event.getInitiator().getId())) {
            throw new NotFoundException("the event does not belong to the user");
        }

        if (updateEventRequestDto.getEventDate() != null
                && updateEventRequestDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("The editing time for the event has expired");
        }

        switch (updateEventRequestDto.getStateAction()) {
            case SEND_TO_REVIEW:
                event.setState(State.PENDING);
                break;
            case CANCEL_REVIEW:
                event.setState(State.CANCELED);
                break;
            case PUBLISH_EVENT:
                event.setState(State.PUBLISHED);
                break;
        }

        if (updateEventRequestDto.getTitle() != null
                && !updateEventRequestDto.getTitle().isBlank()) {
            event.setTitle(updateEventRequestDto.getTitle());
        }

        if (updateEventRequestDto.getAnnotation() != null && !updateEventRequestDto.getAnnotation().isBlank()) {
            event.setAnnotation(updateEventRequestDto.getAnnotation());
        }

        if (updateEventRequestDto.getDescription() != null && !updateEventRequestDto.getDescription().isBlank()) {
            event.setDescription(updateEventRequestDto.getDescription());
        }

        if (updateEventRequestDto.getEventDate() != null) {
            event.setEventDate(updateEventRequestDto.getEventDate());
        }

        if (updateEventRequestDto.getPaid() != null) {
            event.setPaid(updateEventRequestDto.getPaid());
        }

        if (updateEventRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventRequestDto.getParticipantLimit());
        }

        if (updateEventRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventRequestDto.getRequestModeration());
        }

        return eventRepository.save(event);
    }

    @Override
    public List<Request> getRequestsByUser(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));

        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotFoundException("This event not found you");
        }

        return event.getRequests();
    }

    @Override
    public EventRequestStatusUpdateRequestResult changeStatusRequest(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        if (eventRequestStatusUpdateRequest.getStatus() == null
                || eventRequestStatusUpdateRequest.getRequestIds() == null) {
            throw new ConflictException("No status or identifiers to replace");
        }

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));

        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                "Category with eventId " + eventId + "not found"));

        for (Long requestId : eventRequestStatusUpdateRequest.getRequestIds()) {
            Request request = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(
                    "Request with requestId " + requestId + "not found"));
            if (!Status.PENDING.equals(request.getStatus())) {
                throw new ConflictException("The status of the application is unchangeable");
            }

            if (Status.CONFIRMED.equals(eventRequestStatusUpdateRequest.getStatus())) {
                if (event.getParticipantLimit() <= getCountUser(event)) {
                    throw new ConflictException("The limit of participants has expired");
                }

                request.setStatus(Status.CONFIRMED);
            } else if (Status.REJECTED.equals(eventRequestStatusUpdateRequest.getStatus())) {
                request.setStatus(Status.REJECTED);
            }
        }


        var confirmedRequests = requestRepository.findAllByIdInAndStatus(eventRequestStatusUpdateRequest.getRequestIds(),
                Status.CONFIRMED).stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
        var rejectedRequests = requestRepository.findAllByIdInAndStatus(eventRequestStatusUpdateRequest.getRequestIds(),
                Status.REJECTED).stream().map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());

        return EventRequestStatusUpdateRequestResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }

    @Override
    public List<Request> getRequests(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));

        return requestRepository.findAllByRequesterId(userId);
    }

    @Override
    public Request addRequest(Long userId, Long eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));

        var event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                "Category with eventId:" + eventId + "not found"));

        Optional<Request> request = requestRepository.findByRequesterIdAndEventId(userId, eventId);
        if (request.isPresent()) {
            throw new ConflictException("Repeat request");
        }
        if (!State.PUBLISHED.equals(event.getState())) {
            throw new ConflictException("This event has not yet been published");
        }
        if (userId.equals(event.getInitiator().getId())) {
            throw new ConflictException("This is your event");
        }

        if (event.getParticipantLimit() <= getCountUser(event)) {
            throw new ConflictException("The limit of participants has expired");
        }

        var newRequest = Request.builder()
                .id(0L)
                .created(LocalDateTime.now())
                .requesterId(userId)
                .event(event)
                .status(Status.PENDING)
                .build();

        if (event.getRequestModeration() != null && !event.getRequestModeration()) {
            newRequest = newRequest.toBuilder()
                    .status(Status.CONFIRMED)
                    .build();
        }

        return requestRepository.save(newRequest);
    }

    @Override
    public Request cancelRequest(Long userId, Long requestId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("user " + userId + " not found")));

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request was not found"));

        if (!user.getId().equals(userId)) {
            throw new NotFoundException("Request not you");
        }
        if (Status.REJECTED.equals(request.getStatus()) || Status.CANCELED.equals(request.getStatus())) {
            throw new ConflictException("The application has already been cancelled");
        }

        request.setStatus(Status.CANCELED);
        return requestRepository.save(request);
    }

    private Long getCountUser(Event event) {
        return event.getRequests() != null
                ? event.getRequests().stream()
                .filter(req -> Status.CONFIRMED.equals(req.getStatus()))
                .count() : 0;
    }
}
