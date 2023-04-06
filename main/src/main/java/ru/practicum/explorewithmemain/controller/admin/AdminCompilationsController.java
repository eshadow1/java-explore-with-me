package ru.practicum.explorewithmemain.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.compilation.dto.CompilationDto;
import ru.practicum.explorewithmemain.models.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithmemain.models.compilation.mapper.CompilationMapper;
import ru.practicum.explorewithmemain.service.admin.AdminService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {
    private final AdminService adminService;

    public AdminCompilationsController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto postCompilations(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return CompilationMapper.toCompilationDto(adminService.addCompilation(newCompilationDto));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompilationDto deleteCompilations(@PathVariable Long compId) {
        return CompilationMapper.toCompilationDto(adminService.removeCompilation(compId));
    }

    @PatchMapping("/{compId}")
    public CompilationDto patchCompilations(@PathVariable Long compId, @RequestBody NewCompilationDto newCompilationDto) {
        return CompilationMapper.toCompilationDto(adminService.pathCompilation(compId, newCompilationDto));
    }
}
