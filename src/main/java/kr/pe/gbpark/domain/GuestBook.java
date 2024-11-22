package kr.pe.gbpark.domain;

import jakarta.persistence.Entity;
import kr.pe.gbpark.request.GuestBookEdit;
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

    @Builder
    private GuestBook(String title, String content, boolean secret, String writer, String password, String email) {
        super(title, content, secret);
        this.writer = writer;
        this.password = password;
        this.email = email;
    }

    public void edit(GuestBookEdit edit) {
        super.title = edit.getTitle();
        writer = edit.getWriter();
        super.content = edit.getContent();
        email = edit.getEmail();
        secret = edit.isSecret();
    }

    public static GuestBookBuilder builder(String title) {
        return new GuestBookBuilder().title(title);
    }

    public static class GuestBookBuilder {
        private String title;
        private String content;
        private boolean secret = false;
        private String writer;
        private String password;
        private String email;
        GuestBookBuilder() {}

        private GuestBookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public GuestBookBuilder content(String content) {
            this.content = content;
            return this;
        }

        public GuestBookBuilder secret(boolean secret) {
            this.secret = secret;
            return this;
        }

        public GuestBookBuilder writer(String writer) {
            this.writer = writer;
            return this;
        }

        public GuestBookBuilder password(String password) {
            this.password = password;
            return this;
        }

        public GuestBookBuilder email(String email) {
            this.email = email;
            return this;
        }

        public GuestBook build() {
            return new GuestBook(title, content, secret, writer, password, email);
        }
    }
}