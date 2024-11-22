package kr.pe.gbpark.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Builder
public class GuestBookCreate {
    @NotBlank(message = "title is mandatory")
    private final String title;
    
    @NotBlank(message = "writer is mandatory")
    private final String writer;
    
    @ToString.Exclude
    @NotBlank(message = "password is mandatory")
    private final String password;
    
    private final String content;
    private final String email;
    
    @Builder.Default
    private boolean secret = false;

    @Builder
    public static GuestBookCreateBuilder builder(String title, String writer, String password) {
        return new GuestBookCreateBuilder().title(title).writer(writer).password(password);
    }
}