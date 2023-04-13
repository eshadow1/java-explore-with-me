package ru.practicum.explorewithmemain.controller.unknownuser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.category.dto.CategoryDto;
import ru.practicum.explorewithmemain.models.category.mapper.CategoryMapper;
import ru.practicum.explorewithmemain.service.unknownuser.category.UnknownUserCategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/categories")
public class UnknownUserCategoryController {
    private final UnknownUserCategoryService unknownUserCategoryService;

    public UnknownUserCategoryController(UnknownUserCategoryService unknownUserCategoryService) {
        this.unknownUserCategoryService = unknownUserCategoryService;
    }

    @GetMapping
    public List<CategoryDto> getCategories(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return unknownUserCategoryService.getAll(from, size).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        return CategoryMapper.toCategoryDto(unknownUserCategoryService.getCategoryId(catId));
    }
}
