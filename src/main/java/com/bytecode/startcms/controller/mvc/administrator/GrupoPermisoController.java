package com.bytecode.startcms.controller.mvc.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bytecode.startcms.repository.GrupoPermisoRepository;
import com.bytecode.startcms.repository.PermisoRepository;
//**************MAYBE UNREQUIRED*****************
@Controller
@RequestMapping("/admin/grupopermiso")
public class GrupoPermisoController {
	@Autowired
	PermisoRepository permisoRepository;
	@Autowired
	GrupoPermisoRepository repository;
	
	@GetMapping("")
	public ModelAndView getHome(
			@RequestParam(defaultValue = "0", required= false) int id,
			Pageable pageable
			) {
		ModelAndView modelAndView = new ModelAndView();
		
		return null;
	}

}
