package kr.pe.gbpark.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Builder
public class GuestBookCreate {
    @NotBlank(message = "title is mandatory")
    private final String title;

    private String writer;
    private String password;
    private String content;
    private String email;
    @Setter
    private String ip;
    @Builder.Default
    private boolean secret = false;

    @Builder
    public static GuestBookCreateBuilder builder(String title) {
        return new GuestBookCreateBuilder().title(title);
    }
}