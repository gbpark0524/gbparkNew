package kr.pe.gbpark.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.pe.gbpark.request.GuestBookCreate;
import kr.pe.gbpark.request.GuestBookSearch;
import kr.pe.gbpark.response.GuestBookResponse;
import kr.pe.gbpark.response.PaginationResponse;
import kr.pe.gbpark.response.Response;
import kr.pe.gbpark.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static kr.pe.gbpark.response.Response.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService guestBookService;

    @PostMapping("/board/guestbook")
    public void postGuestBook(@RequestBody @Valid GuestBookCreate request, HttpServletRequest httpServletRequest) {
        String remoteAddr = "";
        if (httpServletRequest != null) {
            remoteAddr = httpServletRequest.getHeader("X-FORWARDED-FOR");
            if (StringUtils.isEmpty(remoteAddr)) {
                remoteAddr = httpServletRequest.getRemoteAddr();
                request.setIp(remoteAddr);
            }
        }

        log.info("remoteAddr : {}", remoteAddr);
        log.info("request : {}", request);

        guestBookService.write(request);
    }

    @GetMapping("/board/guestbook/{guestBookId}")
    public Response<?> getGuestBook(@PathVariable(name = "guestBookId") Long id) {
        Optional<GuestBookResponse> guestBookResponse = guestBookService.get(id);
        if (guestBookResponse.isPresent()) {
            return new Response<>(true, SUCCESS_MESSAGE, guestBookResponse.get());
        } else {
            return new Response<>(false, EMPTY_MESSAGE, Optional.empty());
        }
    }

    @GetMapping("/board/guestbooks")
    public PaginationResponse<GuestBookResponse> getPaginationGuestBook(@ModelAttribute GuestBookSearch guestBookSearch) {
        return guestBookService.getList(guestBookSearch);
    }
}
