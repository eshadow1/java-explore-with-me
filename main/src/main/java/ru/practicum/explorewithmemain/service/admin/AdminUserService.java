package ru.practicum.explorewithmemain.service.admin;

import ru.practicum.explorewithmemain.models.user.User;

import java.util.List;

public interface AdminUserService {
    User addUser(User user);

    List<User> getUsers(List<Long> usersId, Integer from, Integer size);

    User removeUser(Long userId);

}
