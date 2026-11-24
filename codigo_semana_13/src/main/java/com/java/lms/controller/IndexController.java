package com.java.lms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

	@GetMapping({"/", "/index"})
	public String inicio(HttpSession session) {
		if (session.getAttribute("usuarioActual") != null) {
			return "redirect:/home";
		}
		return "login";
	}
}
