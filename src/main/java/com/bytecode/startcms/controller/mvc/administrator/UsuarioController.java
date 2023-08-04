package com.bytecode.startcms.controller.mvc.administrator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bytecode.startcms.model.Grupo;
import com.bytecode.startcms.model.Usuario;
import com.bytecode.startcms.repository.GrupoRepository;
import com.bytecode.startcms.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/usuario")
public class UsuarioController {
	@Autowired
	UsuarioRepository repository;
	@Autowired
	GrupoRepository grupoRepository;
	
	@GetMapping("")
	public ModelAndView getHome(
			@RequestParam(defaultValue = "all", required = false) String view_name,
			@RequestParam(defaultValue = "0", required = false) int id,
			Pageable pageable
			) {
		ModelAndView modelAndView = new ModelAndView("administrator/usuario");
		switch(view_name) {
		case "all":
			modelAndView.addObject("usuarios", repository.findAll(pageable));
			modelAndView.addObject("mapaGrupos", getMapaGrupos());
			break;
		case "new":
			modelAndView.addObject("usuario", new Usuario());
			modelAndView.addObject("grupos", grupoRepository.findAll(pageable));
			break;
		case "update":
			modelAndView.addObject("usuario", repository.findById(id));
			modelAndView.addObject("grupos", grupoRepository.findAll(pageable));
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
	@ModelAttribute Usuario usuario
	) {	
		if(usuario.getIdUsuario() > 0) {
			repository.update(usuario);
		} else {
			repository.save(usuario);
		}
		return "redirect:/admin/usuario";
	}
	
	private Map<Long, String> getMapaGrupos()  {
		Pageable pageable = new Pageable();
		Map<Long, String> mapaGrupos = new HashMap<Long, String>();
		List<Grupo> listaGrupos = grupoRepository.findAll(pageable);
		listaGrupos.forEach(grupo -> mapaGrupos.put(grupo.getIdGrupo(), grupo.getNombre()));
		return mapaGrupos;
	}
}
