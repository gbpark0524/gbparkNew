package kr.pe.gbpark.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends Board {
	private String projectUrl;     // 프로젝트 링크 (GitHub, 배포 URL 등)
	private String thumbnailUrl;   // 섬네일 이미지 URL

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "project_tags", joinColumns = @JoinColumn(name = "project_id"))
	private List<ProjectTag> tags = new ArrayList<>();

	@Builder
	private Project(String title, String content, boolean secret, String projectUrl,
					String thumbnailUrl, List<ProjectTag> tags) {
		super(title, content, secret);
		this.projectUrl = projectUrl;
		this.thumbnailUrl = thumbnailUrl;
		if (tags != null) {
			this.tags = tags;
		}
	}

	public static ProjectBuilder builder(String title) {
		return new ProjectBuilder().title(title);
	}

	public static class ProjectBuilder {
		private String title;
		private String content;
		private boolean secret = false;
		private String projectUrl;
		private String thumbnailUrl;
		private List<ProjectTag> tags = new ArrayList<>();

		ProjectBuilder() {}

		private ProjectBuilder title(String title) {
			this.title = title;
			return this;
		}

		public ProjectBuilder content(String content) {
			this.content = content;
			return this;
		}

		public ProjectBuilder secret(boolean secret) {
			this.secret = secret;
			return this;
		}

		public ProjectBuilder projectUrl(String projectUrl) {
			this.projectUrl = projectUrl;
			return this;
		}

		public ProjectBuilder thumbnailUrl(String thumbnailUrl) {
			this.thumbnailUrl = thumbnailUrl;
			return this;
		}

		public ProjectBuilder tags(List<ProjectTag> tags) {
			this.tags = tags;
			return this;
		}

		public Project build() {
			return new Project(title, content, secret, projectUrl, thumbnailUrl, tags);
		}
	}
}