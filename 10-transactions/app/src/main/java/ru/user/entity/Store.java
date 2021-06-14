package ru.user.entity;

import lombok.*;
import ru.user.enums.OrderStateEnum;

import javax.persistence.*;

import java.time.ZonedDateTime;

import static ru.user.entity.Sequence.SEQ_OBJECT;

@Entity
@Table(name = "stores", schema = Schema.PUBLIC)
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
    @Column(name = "user_name")
    private String userName;

    /**
     * дата создания
     */
    @Column(name = "date_created")
    private ZonedDateTime dateCreated;

    /**
     * статус
     */
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StoreStateEnum state;

    /**
     * id заказа
     */
    @Column(name = "order_id")
    private Long orderId;
}
