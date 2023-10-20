package ke.pe.gbpark.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ke.pe.gbpark.request.GuestBookCreate;
import ke.pe.gbpark.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            if (!StringUtils.isEmpty(remoteAddr)) {
                remoteAddr = httpServletRequest.getRemoteAddr();
                request.setIp(remoteAddr);
            }
        }

        log.info("remoteAddr : {}", remoteAddr);
        log.info("request : {}", request.toString());

        guestBookService.write(request);
    }
}
