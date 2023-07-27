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

import com.bytecode.startcms.model.Grupo;
import com.bytecode.startcms.model.common.RepBase;
import com.bytecode.startcms.repository.GrupoRepository;

@RestController
@RequestMapping("/api/v1/grupo")
public class GrupoRestController {
	@Autowired
	private GrupoRepository repository;
	
	@PutMapping
	public ResponseEntity<RepBase> save(@RequestBody Grupo grupo) {
		return ResponseEntity.ok(new RepBase(repository.save(grupo)));
	}
	
	@PostMapping
	public ResponseEntity<RepBase> update(@RequestBody Grupo grupo) {
		return ResponseEntity.ok(new RepBase(repository.update(grupo)));
	}
	
	@GetMapping
	public ResponseEntity<List<Grupo>> findAll (SpringDataWebProperties.Pageable pageable) {
		return ResponseEntity.ok(repository.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Grupo> findById(@PathVariable int id) {
		return ResponseEntity.ok(repository.findById(id));
	}

}
