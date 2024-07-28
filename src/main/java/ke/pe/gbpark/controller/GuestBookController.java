package ke.pe.gbpark.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.pe.gbpark.request.GuestBookCreate;
import ke.pe.gbpark.response.GuestBookResponse;
import ke.pe.gbpark.response.Response;
import ke.pe.gbpark.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

import static ke.pe.gbpark.response.Response.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GuestBookController {

    private final GuestBookService guestBookService;

    @PostMapping("/guestbook")
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

    @GetMapping("/guestbook/{guestBookId}")
    public Response<?> getGuestBook(@PathVariable(name = "guestBookId") Long id) {
        Optional<GuestBookResponse> guestBookResponse = guestBookService.get(id);
        if (guestBookResponse.isPresent()) {
            return new Response<>(true, SUCCESS_MESSAGE, guestBookResponse.get());
        } else {
            return new Response<>(false, EMPTY_MESSAGE, Optional.empty());
        }
    }
    
}
