package com.bytecode.startcms.controller.mvc.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.bytecode.startcms.model.Grupo;
import com.bytecode.startcms.model.GrupoPermiso;
import com.bytecode.startcms.repository.GrupoPermisoRepository;
import com.bytecode.startcms.repository.GrupoRepository;
import com.bytecode.startcms.repository.PermisoRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/grupo")
public class GrupoController {
	@Autowired
	GrupoRepository repository;
	@Autowired
	PermisoRepository permisoRepository;
	@Autowired
	GrupoPermisoRepository grupoPermisoRepository;
	
	@GetMapping("")
	public ModelAndView getHome(
			@RequestParam(defaultValue = "all", required = false) String view_name,
			@RequestParam(defaultValue = "0", required = false) int id,
			Pageable pageable
			) {
		ModelAndView modelAndView = new ModelAndView("administrator/grupo");
		
		switch(view_name) {
		case "all":
			modelAndView.addObject("grupos", repository.findAll(pageable));
			break;
		case "new":
			modelAndView.addObject("grupo", new Grupo());
			break;
		case "update":
			modelAndView.addObject("grupo", repository.findById(id));
			modelAndView.addObject("permisos", permisoRepository.findByIdGrupo(id));
			modelAndView.addObject("permisosDisponibles", permisoRepository.findByNotIdGrupo(id));
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
	@ModelAttribute Grupo grupo
	) {	
		if(grupo.getIdGrupo() > 0) {
			repository.update(grupo);
		} else {
			repository.save(grupo);
		}
		return "redirect:/admin/grupo";
	}
	
	@PostMapping("/addPermiso")
	public String addPermiso(
			@RequestParam int idGrupo,
			@RequestParam int idPermiso
			) {
		GrupoPermiso grupoPermiso = new GrupoPermiso();
		grupoPermiso.setIdGrupo(idGrupo);
		grupoPermiso.setIdPermiso(idPermiso);
		grupoPermisoRepository.save(grupoPermiso);
		
		return "redirect:/admin/grupo?view_name=update&id="+idGrupo;
	}
}
