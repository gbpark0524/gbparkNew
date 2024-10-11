package kr.pe.gbpark.service;

import kr.pe.gbpark.domain.GuestBook;
import kr.pe.gbpark.repository.GuestBookRepository;
import kr.pe.gbpark.request.GuestBookCreate;
import kr.pe.gbpark.request.GuestBookSearch;
import kr.pe.gbpark.response.GuestBookResponse;
import kr.pe.gbpark.response.PaginationResponse;
import kr.pe.gbpark.util.service.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;
    private final SecurityService securityService;

    public void write(GuestBookCreate guestBookCreate) {
        String encodedPassword = securityService.encodePassword(guestBookCreate.getPassword());

        GuestBook guestBook =
                GuestBook.builder(guestBookCreate.getTitle())
                        .content(guestBookCreate.getContent())
                        .writer(guestBookCreate.getWriter())
                        .password(encodedPassword)
                        .email(guestBookCreate.getEmail())
                        .ip(guestBookCreate.getIp())
                        .secret(guestBookCreate.isSecret())
                        .build();

        guestBookRepository.save(guestBook);
    }

    public Optional<GuestBookResponse> get(Long id) {
        return guestBookRepository.findById(id)
                .map(GuestBookResponse::new);
    }

    public PaginationResponse<GuestBookResponse> getList(GuestBookSearch guestBookSearch) {
        Page<GuestBook> guestBookPage = guestBookRepository.getList(guestBookSearch);
        PaginationResponse<GuestBookResponse> paginationResponse = new PaginationResponse<>(guestBookPage, GuestBookResponse.class);
        return paginationResponse;
    }
}
