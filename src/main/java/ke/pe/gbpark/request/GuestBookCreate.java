package ke.pe.gbpark.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuestBookCreate {
    @NotBlank(message = "title is mandatory")
    public String title;

    public String content;
    public String writer;
    public String password;
    public String email;
    public String ip;
}
