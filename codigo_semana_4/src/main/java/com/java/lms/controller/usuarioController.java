package com.java.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.lms.factory.UsuarioFactory;
import com.java.lms.factory.UsuarioFactoryProvider;
import com.java.lms.model.Rol;
import com.java.lms.model.Usuario;
import com.java.lms.repository.UsuarioRepositoryImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class usuarioController {

	@Autowired
	UsuarioRepositoryImpl uRepository;

	@Autowired
	UsuarioFactoryProvider usuarioFactoryProvider;

	@PostMapping("/login")
	public String login(@RequestParam String nickname, @RequestParam String contrasena, HttpSession session, RedirectAttributes redirectAttributes) {
		Usuario usuario = uRepository.findByNickName(nickname);

		if(usuario != null && usuario.getContrasena().equals(contrasena)) {
			session.setAttribute("usuarioActual", usuario);
			return "redirect:/home";
		}

		redirectAttributes.addFlashAttribute("error", "Credenciales incorrectas");
		return "redirect:/";
	}
	
	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioActual");
	    model.addAttribute("usuario", usuario);
		return "home";
	}

	@GetMapping("/admin/usuarios")
	public String formularioCrearUsuario(Model model) {
		model.addAttribute("roles", Rol.values());
		return "crear-usuario";
	}
	
	@PostMapping("/admin/usuarios")
	public String crearUsuario(@RequestParam String nombre,
								@RequestParam String nickname,
								@RequestParam String contrasena,
								@RequestParam Rol rol,
								RedirectAttributes redirectAttributes) {

		if (uRepository.findByNickName(nickname) != null) {
			redirectAttributes.addFlashAttribute("error", "Ya existe un usuario con ese nombre de usuario");
			return "redirect:/admin/usuarios";
		}

		UsuarioFactory factory = usuarioFactoryProvider.getFactory(rol);
		Usuario nuevoUsuario = factory.registrarUsuario(nombre, nickname, contrasena);
		uRepository.save(nuevoUsuario);

		redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente: " + nuevoUsuario.getNickname() + " (" + nuevoUsuario.getRol() + ")");
		return "redirect:/admin/usuarios";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
	    session.invalidate();
	    return "redirect:/";
	}
}
