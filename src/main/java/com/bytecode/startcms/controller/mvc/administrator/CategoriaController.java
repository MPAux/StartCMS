package com.bytecode.startcms.controller.mvc.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bytecode.startcms.model.Categoria;
import com.bytecode.startcms.repository.CategoriaRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/categoria")
public class CategoriaController {
	@Autowired
	CategoriaRepository repository = new CategoriaRepository();
	
	@GetMapping("")
	public ModelAndView getHome(
			@RequestParam(defaultValue = "all", required = false) String view_name,
			@RequestParam(defaultValue = "0", required = false) int id
			) {
		ModelAndView modelAndView = new ModelAndView("administrator/categoria");
		
		switch(view_name) {
		case "all":
			break;
		case "new":
			modelAndView.addObject("categoria", new Categoria());
			break;
		case "update":
			modelAndView.addObject("categoria", repository.findById(id));
			break;
		}
		
		return modelAndView;
	}
	
	@ModelAttribute("requestUri")
	public String contextPath(final HttpServletRequest request) {
	    return request.getRequestURI();
	}
	
	@PostMapping
	public String newAndUpdate(
	@ModelAttribute Categoria categoria
	) {	
		repository.save(categoria);
		return "redirect:/admin/categoria";
	}
}
