package kr.pe.gbpark.response;

import kr.pe.gbpark.domain.Project;
import kr.pe.gbpark.domain.ProjectTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProjectResponse {
	private final Long id;
	private final String title;
	private final String content;
	private final String projectUrl;
	private final String thumbnailUrl;
	private final List<ProjectTag> tags;
	private final String createdDate;
	private final String lastModifiedDate;
	private final boolean secret;

	public ProjectResponse(Project project) {
		this.id = project.getId();
		this.title = project.getTitle();
		this.content = project.isSecret() ? "[비밀글입니다]" : project.getContent();
		this.projectUrl = project.getProjectUrl();
		this.thumbnailUrl = project.getThumbnailUrl();
		this.tags = project.getTags();
		this.createdDate = project.getCreateDate().format(DateTimeFormatter.ISO_DATE_TIME);
		this.lastModifiedDate = project.getLastModifiedDate().format(DateTimeFormatter.ISO_DATE_TIME);
		this.secret = project.isSecret();
	}
}