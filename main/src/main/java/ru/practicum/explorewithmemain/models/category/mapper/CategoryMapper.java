package ru.practicum.explorewithmemain.models.category.mapper;

import ru.practicum.explorewithmemain.models.category.Category;
import ru.practicum.explorewithmemain.models.category.dto.CategoryDto;

public class CategoryMapper {
    public static Category fromCategoryDto(final CategoryDto categoryDto, final Long categoryId) {
        return Category.builder()
                .id(categoryId)
                .name(categoryDto.getName())
                .build();
    }

    public static CategoryDto toCategoryDto(final Category user) {
        return CategoryDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    private CategoryMapper() {}
}
