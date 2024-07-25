package ke.pe.gbpark.domain;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestBook extends Board {
    private String writer;
    private String password;
    private String email;
    private String ip;

    @Builder
    public GuestBook(String title, String content, String writer, String password, String email, String ip) {
        setTitle(title);
        setContent(content);
        this.writer = writer;
        this.password = password;
        this.email = email;
        this.ip = ip;
    }

    public void edit(GuestBookEditor guestBookEditor) {
        setTitle(guestBookEditor.getTitle());
        setContent(guestBookEditor.getContent());
        writer = guestBookEditor.getWriter();
        password = guestBookEditor.getPassword();
        email = guestBookEditor.getEmail();
        ip = guestBookEditor.getIp();
    }
}
