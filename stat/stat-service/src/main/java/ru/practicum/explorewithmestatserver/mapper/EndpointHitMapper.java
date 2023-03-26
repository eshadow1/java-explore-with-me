package ru.practicum.explorewithmestatserver.mapper;

import ru.practicum.explorewithmestatdto.EndpointHitDto;
import ru.practicum.explorewithmestatserver.model.EndpointHit;

public class EndpointHitMapper {
    public static EndpointHit fromEndpointHitDto(final EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .ip(endpointHitDto.getIp())
                .uri(endpointHitDto.getUri())
                .app(endpointHitDto.getApp())
                .timestamp(endpointHitDto.getTimestamp())
                .build();
    }

    public static EndpointHitDto toEndpointHitDto(final EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                .ip(endpointHit.getIp())
                .uri(endpointHit.getUri())
                .app(endpointHit.getApp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }

    private EndpointHitMapper() {
    }
}
