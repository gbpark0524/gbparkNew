package ke.pe.gbpark.request;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GuestBookSearch extends PageSearch {

    private String contentSearch;
    private String writerSearch;
    GuestBookSearch() {
    }

    public GuestBookSearch(Integer page, Integer size, String search, String contentSearch, String writerSearch) {
        super(page, size, search); // 부모 클래스의 생성자 호출
        this.contentSearch = contentSearch;
        this.writerSearch = writerSearch;
    }

}
