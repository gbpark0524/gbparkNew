package ke.pe.gbpark.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuestBookEditor {
    private final String title;
    private final String content;
    private final String writer;
    private final String password;
    private final String email;
    private final String ip;

    @Builder
    public GuestBookEditor(String title, String content, String writer, String password, String email, String ip) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.email = email;
        this.ip = ip;
    }
}
