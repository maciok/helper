package pl.thecode.helper.help;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HelpRepository extends CrudRepository<HelpEntity, Long> {

}
