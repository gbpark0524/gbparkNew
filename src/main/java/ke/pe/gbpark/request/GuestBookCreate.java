package ke.pe.gbpark.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
public class GuestBookCreate {
    @NotBlank(message = "title is mandatory")
    public String title;

    public String writer;
    public String password;
    public String content;
    public String email;
    public String ip;

    @Builder
    public GuestBookCreate(String title, String writer, String password, String content, String email, String ip) {
        this.title = title;
        this.writer = writer;
        this.password = password;
        this.content = content;
        this.email = email;
        this.ip = ip;
    }

}
