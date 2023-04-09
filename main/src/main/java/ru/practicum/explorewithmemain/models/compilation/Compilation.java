package ru.practicum.explorewithmemain.models.compilation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithmemain.models.events.Event;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "compilation", schema = "public")
public class Compilation {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean pinned;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "compilation_events", joinColumns = {@JoinColumn(name = "compilation_id")},
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;
}
