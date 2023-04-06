package ru.practicum.explorewithmemain.service.admin;

import ru.practicum.explorewithmemain.models.category.Category;

public interface AdminCategoryService {

    Category addCategory(Category categoryDto);

    Category removeCategory(Long catId);

    Category patchCategory(Category categoryDto);
}
