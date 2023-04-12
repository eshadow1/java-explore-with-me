package ru.practicum.explorewithmemain.service.unknownuser.category;

import ru.practicum.explorewithmemain.models.category.Category;

import java.util.List;

public interface UnknownUserCategoryService {
    List<Category> getAll(Integer from, Integer size);

    Category getCategoryId(Long catId);
}
