package ru.user.entity;


import lombok.*;

import javax.persistence.*;

import static ru.user.entity.Sequence.SEQ_OBJECT;

@Entity
@Table(name = "products", schema = Schema.PUBLIC)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "gen_nsi", sequenceName = SEQ_OBJECT, allocationSize = 1, schema = Schema.PUBLIC)
public class OrderProduct {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_nsi")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * наименование
     */
    @Column(name = "name")
    private String name;

    /**
     * цена
     */
    @Column(name = "price")
    private Integer price;
}
