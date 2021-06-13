package ru.user.repository;

import org.springframework.data.repository.CrudRepository;
import ru.user.entity.EventLog;

public interface EventLogRepository extends CrudRepository<EventLog, Long> {
}
