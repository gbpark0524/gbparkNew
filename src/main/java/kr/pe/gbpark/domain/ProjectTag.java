package kr.pe.gbpark.domain;

import lombok.Getter;

@Getter
public enum ProjectTag {
	JAVA("Java", "#b07219"),
	SPRING("Spring", "#6DB33F"),
	JAVASCRIPT("JavaScript", "#f7df1e"),
	TYPESCRIPT("TypeScript", "#3178c6"),
	REACT("React", "#61dafb"),
	VUE("Vue.js", "#42b883"),
	ANGULAR("Angular", "#dd0031"),
	PYTHON("Python", "#3572A5"),
	JPA("JPA", "#336791"),
	AWS("AWS", "#FF9900"),
	DOCKER("Docker", "#2496ED"),
	CI_CD("CI/CD", "#4285F4"),
	MOBILE("Mobile", "#A4C639"),
	WEB("Web", "#E34F26"),
	CHROME("Chrome", "#6DB33F"),
	DEV_TOOL("DevTool", "#61dafb");

	private final String displayName;
	private final String colorCode;

	ProjectTag (String displayName, String colorCode) {
		this.displayName = displayName;
		this.colorCode = colorCode;
	}
}