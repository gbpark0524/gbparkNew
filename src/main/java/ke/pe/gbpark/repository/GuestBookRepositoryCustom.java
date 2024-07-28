package ke.pe.gbpark.repository;

import ke.pe.gbpark.domain.GuestBook;
import ke.pe.gbpark.request.GuestBookSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GuestBookRepositoryCustom {
    Page<GuestBook> getList(GuestBookSearch search);
}
