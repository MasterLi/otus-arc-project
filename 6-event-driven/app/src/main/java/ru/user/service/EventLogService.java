package ru.user.service;

import ru.user.enums.EventTypeEnum;

public interface EventLogService {

    /**
     * создание события в event store
     *
     * @param objectId    ключ объекта
     * @param username    пользователь
     * @param eventType   Тип события
     * @param objectValue Текущее значение
     * @return ключ созданного события
     */
    Long createEvent(Long objectId,
                     String username,
                     EventTypeEnum eventType,
                     String objectValue);
}
