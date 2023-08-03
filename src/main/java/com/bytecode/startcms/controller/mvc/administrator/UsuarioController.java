package com.bytecode.startcms.controller.mvc.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.bytecode.startcms.model.Usuario;
import com.bytecode.startcms.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/usuario")
public class UsuarioController {
	@Autowired
	UsuarioRepository repository = new UsuarioRepository();
	
	@GetMapping("")
	public ModelAndView getHome(
			@RequestParam(defaultValue = "all", required = false) String view_name,
			@RequestParam(defaultValue = "0", required = false) int id
			) {
		ModelAndView modelAndView = new ModelAndView("administrator/usuario");
		switch(view_name) {
		case "all":
			break;
		case "new":
			modelAndView.addObject("usuario", new Usuario());
			break;
		case "update":
			modelAndView.addObject("usuario", repository.findById(id));
			break;
		}
		return modelAndView;
	}
	
	@ModelAttribute("requestUri")
	public String contextPath(final HttpServletRequest request) {
	    return request.getRequestURI();
	}
}
