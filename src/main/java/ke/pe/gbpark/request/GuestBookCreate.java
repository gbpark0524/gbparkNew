package ke.pe.gbpark.request;


import lombok.Data;

@Data
public class GuestBookCreate {
    public String title;
    public String content;
    public String writer;
    public String password;
    public String email;
}
