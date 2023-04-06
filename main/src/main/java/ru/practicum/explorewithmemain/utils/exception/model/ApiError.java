package ru.practicum.explorewithmemain.utils.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ApiError {
    private Date timestamp;
    private int status;
    private String massage;
    private String reason;
}
