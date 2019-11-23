package pl.thecode.helper.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = UserEntity.TABLE_NAME)
@Data
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


}
