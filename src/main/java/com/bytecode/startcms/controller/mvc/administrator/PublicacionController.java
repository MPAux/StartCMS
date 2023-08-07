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

import com.bytecode.startcms.model.Post;
import com.bytecode.startcms.model.PostMetadata;
import com.bytecode.startcms.repository.CategoriaRepository;
import com.bytecode.startcms.repository.PostMetadataRepository;
import com.bytecode.startcms.repository.PostRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/publicacion")
public class PublicacionController {
	@Autowired
	PostRepository repository;
	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	PostMetadataRepository postMetadataRepository;
	
	@GetMapping("")
	public ModelAndView getHome(
			@RequestParam(defaultValue = "all", required = false) String view_name,
			@RequestParam(defaultValue = "0", required = false) int id,
			Pageable pageable
			) {
		ModelAndView modelAndView = new ModelAndView("administrator/publicacion");
		switch(view_name) {
		case "all":
			modelAndView.addObject("publicaciones", repository.findAll(pageable));
			break;
		case "new":
			modelAndView.addObject("publicacion", new Post());
			modelAndView.addObject("categorias", categoriaRepository.findAll(pageable));
			modelAndView.addObject("meta_descripcion", new PostMetadata());
			break;
		case "update":
			modelAndView.addObject("publicacion", repository.findById(id));
			modelAndView.addObject("categorias", categoriaRepository.findAll(pageable));
			modelAndView.addObject("meta_descripcion", postMetadataRepository.findByPostIdAndKey(id, "meta_descripcion"));
			break;
		}
		return  modelAndView;
	}
	
	@ModelAttribute("requestUri")
	public String contextPath(final HttpServletRequest request) {
	    return request.getRequestURI();
	}
	
	@PostMapping
	public String newAndUpdate(
	@ModelAttribute Post post,
	@RequestParam(name = "meta_descripcion_text_html") String descripcion,
	@RequestParam(name = "meta_id") int meta_id
	) {	
		PostMetadata meta_descripcion;
		post.setIdUsuario(1);
		post.setTipo("Pruebas");
		
		if(post.getIdPost() > 0) {
			repository.update(post);
			meta_descripcion = postMetadataRepository.findById(meta_id);
			meta_descripcion.setValor(descripcion);
			postMetadataRepository.update(meta_descripcion);
		} else {
			post = repository.findOnSave(post);
			meta_descripcion  = new PostMetadata();
			meta_descripcion.setIdPost(post.getIdPost());
			meta_descripcion.setClave("meta_descripcion");
			meta_descripcion.setValor(descripcion);
			meta_descripcion.setTipo("text/html");
			meta_descripcion.setIdPost(post.getIdPost());
			postMetadataRepository.save(meta_descripcion);
		}
		return "redirect:/admin/publicacion";
	}
}
