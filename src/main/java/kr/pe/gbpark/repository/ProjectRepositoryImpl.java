package kr.pe.gbpark.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.pe.gbpark.domain.Project;
import kr.pe.gbpark.domain.ProjectTag;
import kr.pe.gbpark.request.ProjectSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static kr.pe.gbpark.domain.QProject.project;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<Project> getList(ProjectSearch search) {
		BooleanBuilder whereBuilder = new BooleanBuilder();

		if (search.getTitle() != null && !search.getTitle().isEmpty()) {
			whereBuilder.and(project.title.containsIgnoreCase(search.getTitle()));
		}
		if (search.getContent() != null && !search.getContent().isEmpty()) {
			whereBuilder.and(project.content.contains(search.getContent()));
//			whereBuilder.and(project.content.containsIgnoreCase(search.getContent()));
		}
		if (search.getTags() != null && !search.getTags().isEmpty()) {
			for (ProjectTag tag : search.getTags()) {
				whereBuilder.and(project.tags.contains(tag));
			}
		}

		Long totalCount = jpaQueryFactory.select(project.count())
				.from(project)
				.where(whereBuilder)
				.fetchOne();

		// null 체크 추가
		totalCount = totalCount != null ? totalCount : 0L;

		List<Project> projectList = jpaQueryFactory.selectFrom(project)
				.where(whereBuilder)
				.limit(search.getSize())
				.offset(search.getOffset())
				.orderBy(project.id.desc())
				.fetch();

		return new PageImpl<>(projectList, search.getPageable(), totalCount);
	}
}