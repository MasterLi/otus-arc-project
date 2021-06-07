package ru.user.repository;

import org.springframework.data.repository.CrudRepository;
import ru.user.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(Long id);
}
