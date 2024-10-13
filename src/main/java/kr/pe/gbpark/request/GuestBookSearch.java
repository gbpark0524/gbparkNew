package kr.pe.gbpark.request;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GuestBookSearch extends PageSearch {

    private String title;
    private String content;
    private String writer;
    GuestBookSearch() {
    }

    public GuestBookSearch(Integer page, Integer size, String title, String content, String writer) {
        super(page, size); // 부모 클래스의 생성자 호출
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

}
