package kr.pe.gbpark.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {
	@RequestMapping(value = "/{path:[^.]*}")
	public String redirect() {
		return "forward:/index.html";
	}
}
