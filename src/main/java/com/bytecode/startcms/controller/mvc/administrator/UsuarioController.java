package com.bytecode.startcms.controller.mvc.administrator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/usuario")
public class UsuarioController {
	@GetMapping("")
	public ModelAndView getHome() {
		return new ModelAndView("administrator/usuario");
	}
	
	@ModelAttribute("requestUri")
	public String contextPath(final HttpServletRequest request) {
	    return request.getRequestURI();
	}
}
