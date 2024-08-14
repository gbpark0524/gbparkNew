package ke.pe.gbpark.controller;

import ke.pe.gbpark.response.GithubResponse;
import ke.pe.gbpark.service.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/portlet/github")
public class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/contributions")
    public Mono<List<GithubResponse>> getGithubContributions() {
        return githubService.getContributions();
    }
}
