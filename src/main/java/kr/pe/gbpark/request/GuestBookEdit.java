package kr.pe.gbpark.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GuestBookEdit {
    @NotBlank(message = "title is mandatory")
    private final String title;
    @NotBlank(message = "writer is mandatory")
    private final String writer;
    @ToString.Exclude
    @NotBlank(message = "password is mandatory")
    private final String password;
    private final String content;
    private final String email;
    private final boolean secret;
}
