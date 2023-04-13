package ru.practicum.explorewithmemain.service.unknownuser.category;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithmemain.models.category.Category;
import ru.practicum.explorewithmemain.repository.category.CategoryRepository;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;
import ru.practicum.explorewithmemain.utils.exception.NotFoundException;

import java.util.List;

@Service
@Transactional
public class UnknownUserCategoryServiceImpl implements UnknownUserCategoryService {
    private final CategoryRepository categoryRepository;

    public UnknownUserCategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll(Integer from, Integer size) {
        var pageRequest = new FromPageRequest(from, size, Sort.by(Sort.Direction.DESC, "id"));

        return categoryRepository.findAll(pageRequest).toList();
    }

    @Override
    public Category getCategoryId(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException("Category " + catId + "not found with id "));
    }
}
