package pl.thecode.helper.help;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HelpRepository extends JpaRepository<HelpEntity, Long> {

}
