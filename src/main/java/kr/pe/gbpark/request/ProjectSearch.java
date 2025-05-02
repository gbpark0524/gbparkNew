package kr.pe.gbpark.request;

import kr.pe.gbpark.domain.ProjectTag;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ProjectSearch extends PageSearch {
	private String title;
	private String content;
	private List<ProjectTag> tags;

	ProjectSearch() {
	}

	public ProjectSearch(Integer page, Integer size, String title, String content, List<ProjectTag> tags) {
		super(page, size);
		this.title = title;
		this.content = content;
		this.tags = tags;
	}
}