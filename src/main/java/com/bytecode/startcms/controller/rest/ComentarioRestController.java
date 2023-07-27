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

import com.bytecode.startcms.model.Comentario;
import com.bytecode.startcms.model.common.RepBase;
import com.bytecode.startcms.repository.ComentarioRepository;

@RestController
@RequestMapping("/api/v1/comentario")
public class ComentarioRestController {
	@Autowired
	private ComentarioRepository repository;
	
	@PutMapping
	public ResponseEntity<RepBase> save(@RequestBody Comentario comentario) {
		return ResponseEntity.ok(new RepBase(repository.save(comentario)));
	}
	
	@PostMapping
	public ResponseEntity<RepBase> update(@RequestBody Comentario comentario) {
		return ResponseEntity.ok(new RepBase(repository.update(comentario)));
	}
	
	@GetMapping
	public ResponseEntity<List<Comentario>> findAll (SpringDataWebProperties.Pageable pageable) {
		return ResponseEntity.ok(repository.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Comentario> findById(@PathVariable int id) {
		return ResponseEntity.ok(repository.findById(id));
	}

}
