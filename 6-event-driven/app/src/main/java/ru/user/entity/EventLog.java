package ru.user.entity;

import lombok.*;
import ru.user.enums.EventTypeEnum;

import javax.persistence.*;

import java.time.ZonedDateTime;

import static ru.user.entity.Sequence.SEQ_OBJECT;

@Entity
@Table(name = "event_log", schema = Schema.PUBLIC)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "gen_nsi_event_log", sequenceName = SEQ_OBJECT, allocationSize = 1, schema = Schema.PUBLIC)
public class EventLog {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_nsi_event_log")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * ключ объекта
     */
    @Column(name = "object_id", nullable = false)
    private Long objectId;

    /**
     * пользователь
     */
    @Column(name = "username")
    private String username;

    /**
     * Тип события
     */
    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventTypeEnum eventType;

    /**
     * Текущее значение
     */
    @Column(name = "object_value")
    private String objectValue;

    /**
     * дата создания
     */
    @Column(name = "moment")
    private ZonedDateTime moment;
}
