package ke.pe.gbpark.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ke.pe.gbpark.domain.GuestBook;
import ke.pe.gbpark.request.GuestBookSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static ke.pe.gbpark.domain.QGuestBook.guestBook;

@RequiredArgsConstructor
public class GuestBookRepositoryImpl implements GuestBookRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    
    @Override
    public Page<GuestBook> getList(GuestBookSearch search) {
        Long totalCount = jpaQueryFactory.select(guestBook.count())
                .from(guestBook)
                .fetchFirst();

        List<GuestBook> guestBookList = jpaQueryFactory.selectFrom(guestBook)
                .limit(search.getSize())
                .offset(search.getOffset())
                .orderBy(guestBook.id.desc())
                .fetch();

        return new PageImpl<>(guestBookList, search.getPageable(), totalCount);
    }
}
