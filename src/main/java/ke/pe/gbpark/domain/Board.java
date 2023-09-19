package ke.pe.gbpark.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
