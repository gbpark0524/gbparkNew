package ke.pe.gbpark.service;

import ke.pe.gbpark.domain.GuestBook;
import ke.pe.gbpark.repository.GuestBookRepository;
import ke.pe.gbpark.request.GuestBookCreate;
import ke.pe.gbpark.request.GuestBookSearch;
import ke.pe.gbpark.response.GuestBookResponse;
import ke.pe.gbpark.response.PaginationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<GuestBookResponse> get(Long id) {
        return guestBookRepository.findById(id)
                .map(guestBook -> GuestBookResponse.builder()
                        .id(id)
                        .title(guestBook.getTitle())
                        .content(guestBook.getContent())
                        .writer(guestBook.getWriter())
                        .build());
    }

    public PaginationResponse<GuestBookResponse> getList(GuestBookSearch guestBookSearch) {
        Page<GuestBook> guestBookPage = guestBookRepository.getList(guestBookSearch);
        PaginationResponse<GuestBookResponse> paginationResponse = new PaginationResponse<>(guestBookPage, GuestBookResponse.class);
        return paginationResponse;
    }
}
