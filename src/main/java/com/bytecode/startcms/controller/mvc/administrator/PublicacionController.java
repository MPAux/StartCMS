package com.bytecode.startcms.controller.mvc.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.bytecode.startcms.model.Post;
import com.bytecode.startcms.repository.PostRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/publicacion")
public class PublicacionController {
	@Autowired
	PostRepository repository = new PostRepository();
	
	@GetMapping("")
	public ModelAndView getHome(
			@RequestParam(defaultValue = "all", required = false) String view_name,
			@RequestParam(defaultValue = "0", required = false) int id
			) {
		ModelAndView modelAndView = new ModelAndView("administrator/publicacion");
		switch(view_name) {
		case "all":
			break;
		case "new":
			modelAndView.addObject("publicacion", new Post());
			break;
		case "update":
			modelAndView.addObject("publicacion", repository.findById(id));
			break;
		}
		return  modelAndView;
	}
	
	@ModelAttribute("requestUri")
	public String contextPath(final HttpServletRequest request) {
	    return request.getRequestURI();
	}
}
