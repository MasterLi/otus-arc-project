package ru.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.user.entity.EventLog;
import ru.user.enums.EventTypeEnum;
import ru.user.repository.EventLogRepository;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class EventLogServiceImpl implements EventLogService {
    private final EventLogRepository eventLogRepository;

    /**
     * создание события в event store
     *
     * @param objectId    ключ объекта
     * @param username    пользователь
     * @param eventType   Тип события
     * @param objectValue Текущее значение
     * @return ключ созданного события
     */

    @Override
    public Long createEvent(Long objectId, String username, EventTypeEnum eventType, String objectValue) {
        EventLog eventLog = EventLog.builder()
                .eventType(eventType)
                .moment(ZonedDateTime.now())
                .objectId(objectId)
                .objectValue(objectValue)
                .username(username)
                .build();
        eventLog = eventLogRepository.save(eventLog);
        return eventLog.getId();
    }
}
