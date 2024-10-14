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
    private String writer;
    
    @ToString.Exclude
    @Setter
    @NotBlank(message = "password is mandatory")
    private String password;
    
    private String content;
    private String email;
    
    @Builder.Default
    private boolean secret = false;

    @Builder
    public static GuestBookCreateBuilder builder(String title) {
        return new GuestBookCreateBuilder().title(title);
    }
}