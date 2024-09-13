package kr.pe.gbpark.repository;

import kr.pe.gbpark.domain.GuestBook;
import kr.pe.gbpark.request.GuestBookSearch;
import org.springframework.data.domain.Page;

public interface GuestBookRepositoryCustom {
    Page<GuestBook> getList(GuestBookSearch search);
}
