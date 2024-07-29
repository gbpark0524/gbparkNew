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
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;

    @Builder.Default
    private Integer page = DEFAULT_PAGE;

    @Builder.Default
    private Integer size = DEFAULT_SIZE;

    PageSearch(){}
    
    public PageSearch(Integer page, Integer size) {
        this.page = (page == null || page <= 0) ? DEFAULT_PAGE : Math.min(page, MAX_PAGE);
        this.size = (size == null || size <= 0) ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
    }

    public long getOffset() {
        return (long) (page - 1) * Math.min(size, MAX_SIZE);
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
    
    public int getDefaultPage() {return DEFAULT_PAGE;}
    public int getDefaultSize() {return DEFAULT_SIZE;}
}

