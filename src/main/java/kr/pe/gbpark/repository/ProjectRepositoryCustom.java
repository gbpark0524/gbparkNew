package kr.pe.gbpark.repository;

import kr.pe.gbpark.domain.Project;
import kr.pe.gbpark.request.ProjectSearch;
import org.springframework.data.domain.Page;

public interface ProjectRepositoryCustom {
	Page<Project> getList(ProjectSearch search);
}