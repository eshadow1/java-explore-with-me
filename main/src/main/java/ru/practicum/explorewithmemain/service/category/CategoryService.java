package ru.practicum.explorewithmemain.service.category;

import ru.practicum.explorewithmemain.models.category.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll(Integer from, Integer size);

    Category getCategoryId(Long catId);
}
