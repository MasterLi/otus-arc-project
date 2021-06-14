package ru.user.entity;

import lombok.*;

import javax.persistence.*;

import static ru.user.entity.Sequence.SEQ_OBJECT;

@Entity
@Table(name = "order_detail_log", schema = Schema.PUBLIC)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "gen_nsi", sequenceName = SEQ_OBJECT, allocationSize = 1, schema = Schema.PUBLIC)
public class OrderDetailLog {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_nsi")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * заказ
     */
    @JoinColumn(name = "order_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderProduct orderProduct;

    @Column(name = "product_count", nullable = false)
    private Integer itemCount;


    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}
