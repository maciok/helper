package pl.thecode.helper.user;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = UserEntity.TABLE_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
class UserEntity {
    static final String TABLE_NAME = "app_user";
    private static final String SEQ_NAME = TABLE_NAME + "_seq";
    private static final String IDENTITY_GENERATOR = SEQ_NAME + "_gen";
    private static final int SEQ_INITIAL_VALUE = 1000;
    private static final int SEQ_INCREMENT_BY_VALUE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IDENTITY_GENERATOR)
    @SequenceGenerator(name = IDENTITY_GENERATOR, sequenceName = SEQ_NAME, allocationSize = SEQ_INCREMENT_BY_VALUE, initialValue = SEQ_INITIAL_VALUE)
    private Long id;

    @Basic
    private String uuid;

    @Basic
    private Integer age;

    @Basic
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String roles;

    static UserEntity create(String uuid, Integer age, List<Role> roles) {
        return new UserEntity(null, uuid, age, roles.stream().map(Role::name).collect(Collectors.joining(",")));
    }




}
