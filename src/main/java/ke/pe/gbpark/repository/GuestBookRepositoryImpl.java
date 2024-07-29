package ke.pe.gbpark.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ke.pe.gbpark.domain.GuestBook;
import ke.pe.gbpark.domain.QBoard;
import ke.pe.gbpark.domain.QGuestBook;
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
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (search.getTitle() != null && !search.getTitle().isEmpty()) {
            whereBuilder.and(guestBook.title.containsIgnoreCase(search.getTitle()));
        }
        if (search.getContent() != null && !search.getContent().isEmpty()) {
            whereBuilder.and(guestBook.content.containsIgnoreCase(search.getContent()));
        }
        if (search.getWriter() != null && !search.getWriter().isEmpty()) {
            whereBuilder.and(guestBook.writer.containsIgnoreCase(search.getWriter()));
        }

        Long totalCount = jpaQueryFactory.select(guestBook.count())
                .from(guestBook)
                .where(whereBuilder)
                .fetchFirst();

        List<GuestBook> guestBookList = jpaQueryFactory.selectFrom(guestBook)
                .where(whereBuilder)
                .limit(search.getSize())
                .offset(search.getOffset())
                .orderBy(guestBook.id.desc())
                .fetch();

        return new PageImpl<>(guestBookList, search.getPageable(), totalCount);
    }
}
