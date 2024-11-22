package kr.pe.gbpark.service;

import kr.pe.gbpark.domain.GuestBook;
import kr.pe.gbpark.exception.InvalidPassword;
import kr.pe.gbpark.exception.NotFound;
import kr.pe.gbpark.repository.GuestBookRepository;
import kr.pe.gbpark.request.GuestBookCreate;
import kr.pe.gbpark.request.GuestBookEdit;
import kr.pe.gbpark.request.GuestBookSearch;
import kr.pe.gbpark.response.GuestBookResponse;
import kr.pe.gbpark.response.PaginationResponse;
import kr.pe.gbpark.util.security.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestBookService {
    private final GuestBookRepository guestBookRepository;
    private final EncryptionUtil encryptionUtil;

    public void write(GuestBookCreate guestBookCreate) {
        GuestBook guestBook =
                GuestBook.builder(guestBookCreate.getTitle())
                        .content(guestBookCreate.getContent())
                        .writer(guestBookCreate.getWriter())
                        .password(encryptionUtil.encodePassword(guestBookCreate.getPassword()))
                        .email(guestBookCreate.getEmail())
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
        return new PaginationResponse<>(guestBookPage, GuestBookResponse.class);
    }

    @Transactional
    public void edit(Long id, GuestBookEdit guestBookEdit) {
        GuestBook guestBook = guestBookRepository.findById(id)
                .orElseThrow(NotFound::new);

        if (!encryptionUtil.matchPassword(guestBook.getPassword(), guestBook.getPassword())) {
            throw new InvalidPassword();
        }
        
        guestBook.edit(guestBookEdit);
        guestBookRepository.save(guestBook);
    }

    public void deleteGuestBook(Long id, String password) {
        Optional<GuestBook> byId = guestBookRepository.findById(id);
        if (byId.isPresent()) {
            GuestBook guestBook = byId.get();
            boolean matched = encryptionUtil.matchPassword(password, guestBook.getPassword());
            if (matched) {
                guestBookRepository.delete(guestBook);
            } else {
                throw new InvalidPassword();
            }
        } else {
            throw new NotFound();
        }
    }
}
