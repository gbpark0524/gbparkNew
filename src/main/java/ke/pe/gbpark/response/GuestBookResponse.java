package ke.pe.gbpark.response;

import ke.pe.gbpark.domain.GuestBook;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GuestBookResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String writer;

    public GuestBookResponse(GuestBook guestBook) {
        this.id = guestBook.getId();
        this.title = guestBook.getTitle();
        this.content = guestBook.getContent();
        this.writer = guestBook.getWriter();
    }

    @Builder
    public GuestBookResponse(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
        this.writer = writer;
    }
}
