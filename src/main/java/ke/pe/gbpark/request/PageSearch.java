package ke.pe.gbpark.request;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@SuperBuilder
public class PageSearch {

    private static final int MAX_PAGE = 999;
    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    PageSearch(){}
    
    public PageSearch(Integer page, Integer size) {
        this.page = (page <= 0) ? 1 : Math.min(page, MAX_PAGE);
        this.size = (size <= 0) ? 10 : Math.min(size, MAX_SIZE);
    }

    public long getOffset() {
        return (long) (page - 1) * Math.min(size, MAX_SIZE);
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}

