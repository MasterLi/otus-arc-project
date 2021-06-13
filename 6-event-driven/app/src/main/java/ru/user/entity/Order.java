package ru.user.entity;

import lombok.*;
import ru.user.enums.OrderStateEnum;

import javax.persistence.*;

import java.time.ZonedDateTime;

import static ru.user.entity.Sequence.SEQ_OBJECT;

@Entity
@Table(name = "orders", schema = Schema.PUBLIC)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "gen_nsi", sequenceName = SEQ_OBJECT, allocationSize = 1, schema = Schema.PUBLIC)
public class Order {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_nsi")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * пользователь
     */
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * дата создания
     */
    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    /**
     * пользователя
     */
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderStateEnum state;

    /**
     * маркер идемпотентности
     */
    @Column(name = "idempotent_marker")
    private Long idempotentMarker;
}
