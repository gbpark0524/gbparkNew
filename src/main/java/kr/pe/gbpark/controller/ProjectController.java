package kr.pe.gbpark.controller;

import kr.pe.gbpark.request.ProjectSearch;
import kr.pe.gbpark.response.PaginationResponse;
import kr.pe.gbpark.response.ProjectResponse;
import kr.pe.gbpark.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board/project")
public class ProjectController {

	private final ProjectService projectService;

	@GetMapping("")
	public PaginationResponse<ProjectResponse> getPaginationProject(@ModelAttribute ProjectSearch projectSearch) {
		return projectService.getList(projectSearch);
	}
}