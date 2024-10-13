package kr.pe.gbpark.repository;

import kr.pe.gbpark.domain.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, GuestBookRepositoryCustom {

}
