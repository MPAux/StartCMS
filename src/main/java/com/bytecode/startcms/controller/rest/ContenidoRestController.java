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

import com.bytecode.startcms.model.Contenido;
import com.bytecode.startcms.model.common.RepBase;
import com.bytecode.startcms.repository.ContenidoRepository;

@RestController
@RequestMapping("/api/v1/contenido")
public class ContenidoRestController {
	@Autowired
	private ContenidoRepository repository;
	
	@PutMapping
	public ResponseEntity<RepBase> save(@RequestBody Contenido contenido) {
		return ResponseEntity.ok(new RepBase(repository.save(contenido)));
	}
	
	@PostMapping
	public ResponseEntity<RepBase> update(@RequestBody Contenido contenido) {
		return ResponseEntity.ok(new RepBase(repository.update(contenido)));
	}
	
	@GetMapping
	public ResponseEntity<List<Contenido>> findAll (SpringDataWebProperties.Pageable pageable) {
		return ResponseEntity.ok(repository.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Contenido> findById(@PathVariable int id) {
		return ResponseEntity.ok(repository.findById(id));
	}

}
