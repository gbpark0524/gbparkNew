package ke.pe.gbpark.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestBook{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String writer;
    private String password;
    private String email;
    private String ip;

    @Builder
    public GuestBook(String title, String content, String writer, String password, String email, String ip) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.email = email;
        this.ip = ip;
    }

    public void edit(GuestBookEditor guestBookEditor) {
        title = guestBookEditor.getTitle();
        content = guestBookEditor.getContent();
        writer = guestBookEditor.getWriter();
        password = guestBookEditor.getPassword();
        email = guestBookEditor.getEmail();
        ip = guestBookEditor.getIp();
    }
}
