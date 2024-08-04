package ke.pe.gbpark.response;

import ke.pe.gbpark.domain.GuestBook;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class GuestBookResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String date;

    public GuestBookResponse(GuestBook guestBook) {
        this.id = guestBook.getId();
        this.title = guestBook.getTitle();
        this.content = guestBook.getContent();
        this.writer = guestBook.getWriter();
        this.date = guestBook.getLastModifiedDate().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Builder
    public GuestBookResponse(Long id, String title, String content, String writer, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.date = date;
    }
}
