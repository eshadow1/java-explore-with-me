package ru.practicum.explorewithmemain.models.user.mapper;

import ru.practicum.explorewithmemain.models.user.User;
import ru.practicum.explorewithmemain.models.user.dto.UserDto;
import ru.practicum.explorewithmemain.models.user.dto.UserShortDto;

public class UserMapper {
    public static User fromUserDto(final UserDto userDto, final Long userId) {
        return User.builder()
                .id(userId)
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }

    public static UserDto toUserDto(final User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }
    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    private UserMapper() {}
}
