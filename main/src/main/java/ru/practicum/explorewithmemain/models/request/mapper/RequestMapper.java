package ru.practicum.explorewithmemain.models.request.mapper;

import ru.practicum.explorewithmemain.models.request.Request;
import ru.practicum.explorewithmemain.models.request.dto.ParticipationRequestDto;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .requester(request.getRequesterId())
                .event(request.getEvent().getId())
                .status(request.getStatus())
                .created(request.getCreated())
                .build();
    }

    private RequestMapper() {}
}
