package ru.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

import static ru.user.entity.Sequence.SEQ_OBJECT;

@Entity
@Table(name = "users", schema = Schema.PUBLIC)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(name = "gen_nsi", sequenceName = SEQ_OBJECT, allocationSize = 1, schema = Schema.PUBLIC)
public class User {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_nsi")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /**
     * Имя входа пользователя
     */
    @Column(name = "username", length = 1024)
    private String username;

    @Column(name = "firstName", length = 1024)
    private String firstName;

    @Column(name = "lastName", length = 1024)
    private String lastName;

    @Column(name = "email", length = 1024)
    private String email;

    @Column(name = "phone", length = 21)
    private String phone;
}
