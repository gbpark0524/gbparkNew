package kr.pe.gbpark.controller;

import jakarta.validation.Valid;
import kr.pe.gbpark.request.GuestBookCreate;
import kr.pe.gbpark.request.GuestBookSearch;
import kr.pe.gbpark.response.GuestBookResponse;
import kr.pe.gbpark.response.PaginationResponse;
import kr.pe.gbpark.response.Response;
import kr.pe.gbpark.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Optional;

import static kr.pe.gbpark.response.Response.EMPTY_MESSAGE;
import static kr.pe.gbpark.response.Response.SUCCESS_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board/guestbook")
public class GuestBookController {

    private final GuestBookService guestBookService;

    @PostMapping("")
    public void postGuestBook(@RequestBody @Valid GuestBookCreate request) {
        guestBookService.write(request);
    }

    @GetMapping("/{guestBookId}")
    public Response<?> getGuestBook(@PathVariable(name = "guestBookId") Long id) {
        Optional<GuestBookResponse> guestBookResponse = guestBookService.get(id);
        if (guestBookResponse.isPresent()) {
            return new Response<>(true, SUCCESS_MESSAGE, guestBookResponse.get());
        } else {
            return new Response<>(false, EMPTY_MESSAGE, Optional.empty());
        }
    }

    @GetMapping("")
    public PaginationResponse<GuestBookResponse> getPaginationGuestBook(@ModelAttribute GuestBookSearch guestBookSearch) {
        return guestBookService.getList(guestBookSearch);
    }
    
    @DeleteMapping("/{guestBookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGuestBook(@PathVariable(name = "guestBookId") Long id, @RequestHeader("Board-Password") String password) {
        guestBookService.deleteGuestBook(id, new String(Base64.getDecoder().decode(password)));
    }
}
