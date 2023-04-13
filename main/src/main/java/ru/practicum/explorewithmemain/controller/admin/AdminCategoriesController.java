package ru.practicum.explorewithmemain.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.category.dto.CategoryDto;
import ru.practicum.explorewithmemain.models.category.mapper.CategoryMapper;
import ru.practicum.explorewithmemain.service.admin.AdminService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    private final AdminService adminService;

    public AdminCategoriesController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto postCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return CategoryMapper.toCategoryDto(adminService.addCategory(CategoryMapper.fromCategoryDto(categoryDto, 0L)));
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CategoryDto deleteCategory(@PathVariable Long catId) {
        return CategoryMapper.toCategoryDto(adminService.removeCategory(catId));
    }

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@PathVariable Long catId, @RequestBody @Valid CategoryDto categoryDto) {
        return CategoryMapper.toCategoryDto(adminService.patchCategory(CategoryMapper.fromCategoryDto(categoryDto, catId)));
    }
}
