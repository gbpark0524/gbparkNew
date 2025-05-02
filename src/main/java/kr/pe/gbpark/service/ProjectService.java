package kr.pe.gbpark.service;

import kr.pe.gbpark.domain.Project;
import kr.pe.gbpark.repository.ProjectRepository;
import kr.pe.gbpark.request.ProjectSearch;
import kr.pe.gbpark.response.PaginationResponse;
import kr.pe.gbpark.response.ProjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ProjectRepository projectRepository;

	@Transactional(readOnly = true)
	public PaginationResponse<ProjectResponse> getList(ProjectSearch projectSearch) {
		Page<Project> projectPage = projectRepository.getList(projectSearch);
		return new PaginationResponse<>(projectPage, ProjectResponse.class);
	}
}