package com.bytecode.startcms.controller.mvc.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.bytecode.startcms.model.Permiso;
import com.bytecode.startcms.repository.PermisoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/permiso")
public class PermisoController {
	@Autowired
	PermisoRepository repository;
	
	@GetMapping("")
	public ModelAndView getHome(
			@RequestParam(defaultValue = "all", required = false) String view_name,
			@RequestParam(defaultValue = "0", required = false) int id,
			Pageable pageable
			) {
		ModelAndView modelAndView = new ModelAndView("administrator/permiso");
		
		switch(view_name) {
		case "all":
			modelAndView.addObject("permisos", repository.findAll(pageable));
			break;
		case "new":
			modelAndView.addObject("permiso", new Permiso());
			break;
		case "update":
			modelAndView.addObject("permiso", repository.findById(id));
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
	@ModelAttribute Permiso permiso
	) {	
		if(permiso.getIdPermiso() > 0) {
			repository.update(permiso);
		} else {
			repository.save(permiso);
		}
		return "redirect:/admin/permiso";
	}
}
