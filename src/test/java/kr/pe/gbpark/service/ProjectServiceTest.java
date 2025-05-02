package kr.pe.gbpark.service;

import jakarta.transaction.Transactional;
import kr.pe.gbpark.domain.Project;
import kr.pe.gbpark.domain.ProjectTag;
import kr.pe.gbpark.repository.ProjectRepository;
import kr.pe.gbpark.request.ProjectSearch;
import kr.pe.gbpark.response.PaginationResponse;
import kr.pe.gbpark.response.ProjectResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ProjectServiceTest {
	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectRepository projectRepository;

	@BeforeEach
	void setUp() {
		projectRepository.deleteAll();
	}

	@Test
	@DisplayName("ProjectService getList 테스트")
	void getList() {
		// Given
		int totalProjects = 20;
		int pageSize = 10;
		int pageNumber = 1;

		List<Project> projects = createProjects(totalProjects);
		projectRepository.saveAll(projects);

		ProjectSearch search = ProjectSearch.builder()
				.page(pageNumber)
				.build();

		// When
		PaginationResponse<ProjectResponse> response = projectService.getList(search);

		// Then
		assertThat(response.getSize()).isEqualTo(pageSize);
		assertThat(response.getItems().get(0).getTitle()).isEqualTo("프로젝트 19");
		assertThat(response.getTotalPage()).isEqualTo(2);
		assertThat(response.getTotalCount()).isEqualTo(totalProjects);
		assertThat(response.getPage()).isEqualTo(pageNumber);
	}

	@Test
	@DisplayName("ProjectService getList 기본 값 테스트")
	void getListWithDefault() {
		// Given
		List<Project> projects = IntStream.range(0, 20)
				.mapToObj(i -> Project.builder("프로젝트 " + i)
						.content("내용 " + i)
						.build())
				.collect(Collectors.toList());

		projectRepository.saveAll(projects);

		ProjectSearch projectSearch = ProjectSearch.builder().build();

		// When
		PaginationResponse<ProjectResponse> response = projectService.getList(projectSearch);

		// Then
		assertEquals(projectSearch.getDefaultSize(), response.getItems().size());
		assertEquals(projectSearch.getDefaultPage(), (int) response.getPage());
	}

	@Test
	@DisplayName("태그로 프로젝트 필터링 테스트")
	void getListWithTags() {
		// Given
		Project javaProject = Project.builder("Java 프로젝트")
				.content("Java 프로젝트 내용")
				.tags(Arrays.asList(ProjectTag.JAVA, ProjectTag.SPRING))
				.build();

		Project reactProject = Project.builder("React 프로젝트")
				.content("React 프로젝트 내용")
				.tags(Arrays.asList(ProjectTag.REACT, ProjectTag.JAVASCRIPT))
				.build();

		Project pythonProject = Project.builder("Python 프로젝트")
				.content("Python 프로젝트 내용")
				.tags(Arrays.asList(ProjectTag.PYTHON, ProjectTag.DEV_TOOL))
				.build();

		projectRepository.saveAll(Arrays.asList(javaProject, reactProject, pythonProject));

		ProjectSearch javaSearch = ProjectSearch.builder()
				.tags(List.of(ProjectTag.JAVA))
				.build();

		// When
		PaginationResponse<ProjectResponse> response = projectService.getList(javaSearch);

		// Then
		assertThat(response.getItems()).hasSize(1);
		assertThat(response.getItems().get(0).getTitle()).isEqualTo("Java 프로젝트");
		assertThat(response.getItems().get(0).getTags()).contains(ProjectTag.JAVA);
	}

	@Test
	@DisplayName("제목으로 프로젝트 검색 테스트")
	void getListWithTitle() {
		// Given
		List<Project> projects = Arrays.asList(
				Project.builder("스프링 프로젝트").content("스프링 내용").build(),
				Project.builder("리액트 프로젝트").content("리액트 내용").build(),
				Project.builder("파이썬 프로젝트").content("파이썬 내용").build()
		);

		projectRepository.saveAll(projects);

		ProjectSearch search = ProjectSearch.builder()
				.title("스프링")
				.build();

		// When
		PaginationResponse<ProjectResponse> response = projectService.getList(search);

		// Then
		assertThat(response.getItems()).hasSize(1);
		assertThat(response.getItems().get(0).getTitle()).isEqualTo("스프링 프로젝트");
	}

	@Test
	@DisplayName("내용으로 프로젝트 검색 테스트")
	void getListWithContent() {
		// Given
		List<Project> projects = Arrays.asList(
				Project.builder("스프링 프로젝트").content("스프링 내용").build(),
				Project.builder("리액트 프로젝트").content("React 내용").build(),
				Project.builder("파이썬 프로젝트").content("파이썬 내용").build()
		);

		projectRepository.saveAll(projects);

		ProjectSearch search = ProjectSearch.builder()
				.content("react")
				.build();

		// When
		PaginationResponse<ProjectResponse> response = projectService.getList(search);

		// Then
		assertThat(response.getItems()).hasSize(1);
		assertThat(response.getItems().get(0).getTitle()).isEqualTo("리액트 프로젝트");
	}

	private List<Project> createProjects(int count) {
		return IntStream.range(0, count)
				.mapToObj(i -> Project.builder("프로젝트 " + i)
						.content("내용 " + i)
						.projectUrl("https://example.com/project" + i)
						.thumbnailUrl("https://example.com/thumbnail" + i + ".jpg")
						.tags(List.of(i % 2 == 0 ? ProjectTag.JAVA : ProjectTag.REACT))
						.build())
				.collect(Collectors.toList());
	}
}