package ru.practicum.explorewithmemain.models.events.mapper;

import ru.practicum.explorewithmemain.models.category.mapper.CategoryMapper;
import ru.practicum.explorewithmemain.models.events.Event;
import ru.practicum.explorewithmemain.models.events.dto.EventFullDto;
import ru.practicum.explorewithmemain.models.events.dto.EventShortDto;
import ru.practicum.explorewithmemain.models.user.mapper.UserMapper;

public class EventMapper {
    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getRequests())
                .createdOn(event.getCreatedOn())
                .eventDate(event.getEventDate())
                .description(event.getDescription())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .categoryDto(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getRequests())
                .eventDate(event.getEventDate())
                .userShortDto(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    private EventMapper() {}
}
