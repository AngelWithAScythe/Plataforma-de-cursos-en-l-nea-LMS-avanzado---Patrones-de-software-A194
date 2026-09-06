package com.java.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.lms.model.Usuario;
import com.java.lms.repository.UsuarioRepositoryImpl;

@Controller
public class usuarioController {

	@Autowired
	UsuarioRepositoryImpl uRepository;

	@PostMapping("/login")
	public String login(@RequestParam String nickname, @RequestParam String contrasena, RedirectAttributes redirectAttributes) {
		Usuario usuario = uRepository.findByNickName(nickname);

		if(usuario != null && usuario.getContrasena().equals(contrasena)) {
			return "redirect:/home";
		}

		redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
		return "redirect:/";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
}
