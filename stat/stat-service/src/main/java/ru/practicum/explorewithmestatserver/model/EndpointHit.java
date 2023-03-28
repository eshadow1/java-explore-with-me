package ru.practicum.explorewithmestatserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "endpoints", schema = "public")
public class EndpointHit {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "app")
    @NotBlank
    private String app;

    @Column(name = "uri")
    @NotBlank
    private String uri;

    @Column(name = "ip")
    @NotBlank
    private String ip;

    @Column(name = "timestamp_request")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
