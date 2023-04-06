package ru.practicum.explorewithmemain.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithmemain.models.user.dto.UserDto;
import ru.practicum.explorewithmemain.models.user.mapper.UserMapper;
import ru.practicum.explorewithmemain.service.admin.AdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin/users")
@Validated
public class AdminUsersController {
    private final AdminService adminService;

    public AdminUsersController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false)  List<Long> ids,
                                  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return adminService.getUsers(ids, from, size).stream()
        .map(UserMapper::toUserDto)
        .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto postUser(@RequestBody @Valid UserDto userDto) {
        return UserMapper.toUserDto(adminService.addUser(UserMapper.fromUserDto(userDto, (long)0)));
    }

    @DeleteMapping({"/{userId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserDto deleteUser(@PathVariable Long userId) {
        return UserMapper.toUserDto(adminService.removeUser(userId));
    }
}


