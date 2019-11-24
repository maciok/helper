package pl.thecode.helper.user;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    public static final String DELIMITER = ",";

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

    @Basic
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String disabilities;

    static UserEntity create(String uuid, Integer age, List<Role> roles, List<Disabilities> disabilities) {
        String disabilitiesAsString = disabilities.stream().map(Disabilities::name).collect(Collectors.joining(DELIMITER));

        String rolesAsString = roles.stream().map(Role::name).collect(Collectors.joining(DELIMITER));
        return new UserEntity(null, uuid, age, rolesAsString, disabilitiesAsString);
    }

    List<Role> getRoles() {
        return Stream.of(roles.split(DELIMITER))
                     .filter(not(String::isEmpty))
                     .map(Role::valueOf)
                     .collect(Collectors.toUnmodifiableList());
    }

    List<Disabilities> getDisabilities() {
        return Stream.of(disabilities.split(DELIMITER))
                     .filter(not(String::isEmpty))
                     .map(Disabilities::valueOf)
                     .collect(Collectors.toUnmodifiableList());
    }


    UserResponse createDto(String givenName, String familyName, String picture) {
        return new UserResponse(givenName, familyName, picture, age, getRoles(), getDisabilities(), List.of());
    }
}
