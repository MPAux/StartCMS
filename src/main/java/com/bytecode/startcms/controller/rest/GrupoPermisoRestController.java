package com.bytecode.startcms.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytecode.startcms.model.GrupoPermiso;
import com.bytecode.startcms.model.common.RepBase;
import com.bytecode.startcms.repository.GrupoPermisoRepository;

@RestController
@RequestMapping("/api/v1/grupoPermiso")
public class GrupoPermisoRestController {
	@Autowired
	private GrupoPermisoRepository repository;
	
	@PutMapping
	public ResponseEntity<RepBase> save(@RequestBody GrupoPermiso grupoPermiso) {
		return ResponseEntity.ok(new RepBase(repository.save(grupoPermiso)));
	}
	
	@PostMapping
	public ResponseEntity<RepBase> update(@RequestBody GrupoPermiso grupoPermiso) {
		return ResponseEntity.ok(new RepBase(repository.update(grupoPermiso)));
	}
	
	@GetMapping
	public ResponseEntity<List<GrupoPermiso>> findAll (SpringDataWebProperties.Pageable pageable) {
		return ResponseEntity.ok(repository.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GrupoPermiso> findById(@PathVariable int id) {
		return ResponseEntity.ok(repository.findById(id));
	}

}
