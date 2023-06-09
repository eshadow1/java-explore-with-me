package ru.practicum.explorewithmemain.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithmemain.models.user.User;
import ru.practicum.explorewithmemain.utils.database.FromPageRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByIdIn(List<Long> usersId, FromPageRequest pageRequest);

    Optional<User> findByName(String name);
}
