package ke.pe.gbpark.domain;

import jakarta.persistence.DiscriminatorValue;
import lombok.Builder;
import lombok.Getter;

@Getter
@DiscriminatorValue("GUESTBOOK")
public class GuestBook extends Board{
    private String writer;
    private String password;
    private String email;
    private String ip;

    @Builder
    public GuestBook(String title, String content, String writer, String password, String email, String ip) {
        super(title, content);
        this.writer = writer;
        this.password = password;
        this.email = email;
        this.ip = ip;
    }
}
