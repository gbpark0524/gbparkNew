package ke.pe.gbpark.repository;

import ke.pe.gbpark.domain.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, GuestBookRepositoryCustom {

}
