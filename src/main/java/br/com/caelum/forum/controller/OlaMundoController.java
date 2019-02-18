package br.com.caelum.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OlaMundoController {
	@ResponseBody
	@RequestMapping("/ola")
	public String ola() {
		return "Hello World";
	}
}
