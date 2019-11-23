package pl.thecode.helper.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUuid(String uuid);
}
