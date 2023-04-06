package ru.practicum.explorewithmemain.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private Status status;
}
