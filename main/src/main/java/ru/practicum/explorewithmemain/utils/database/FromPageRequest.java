package ru.practicum.explorewithmemain.utils.database;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class FromPageRequest extends PageRequest {
    private final int from;

    public FromPageRequest(int from, int size, Sort sort) {
        super(from / size, size, sort);
        this.from = from;
    }

    @Override
    public long getOffset() {
        return from;
    }
}