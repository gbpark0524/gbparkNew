package ke.pe.gbpark.service;

import ke.pe.gbpark.domain.GuestBook;
import ke.pe.gbpark.repository.GuestBookRepository;
import ke.pe.gbpark.request.GuestBookCreate;
import ke.pe.gbpark.response.GuestBookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;

    public void write(GuestBookCreate guestBookCreate) {
        GuestBook guestBook =
                GuestBook.builder()
                        .title(guestBookCreate.getTitle())
                        .content(guestBookCreate.getContent())
                        .writer(guestBookCreate.getWriter())
                        .password(guestBookCreate.getPassword())
                        .email(guestBookCreate.getEmail())
                        .ip(guestBookCreate.getIp())
                        .build();

        guestBookRepository.save(guestBook);
    }

    public GuestBookResponse get(Long id) {
        GuestBook guestBook = guestBookRepository.findById(id).orElseThrow();
        return GuestBookResponse.builder()
                .id(id)
                .title(guestBook.getTitle())
                .content(guestBook.getContent())
                .writer(guestBook.getWriter())
                .build();
    }
}
