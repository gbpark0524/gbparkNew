package ke.pe.gbpark.response;

import ke.pe.gbpark.domain.GuestBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
public class GuestBookResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String date;
    private final boolean secret;

    public GuestBookResponse(GuestBook guestBook) {
        this.id = guestBook.getId();
        this.title = guestBook.getTitle();
        this.content = guestBook.getContent();
        this.writer = guestBook.getWriter();
        this.date = guestBook.getLastModifiedDate().format(DateTimeFormatter.ISO_DATE_TIME);
        this.secret = guestBook.isSecret();
    }

    @Builder
    public static GuestBookResponseBuilder builder(String title) {
        return new GuestBookResponseBuilder().title(title);
    }
}
