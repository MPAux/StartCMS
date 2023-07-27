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

import com.bytecode.startcms.model.Usuario;
import com.bytecode.startcms.model.common.RepBase;
import com.bytecode.startcms.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioRestController {
	@Autowired
	private UsuarioRepository repository;
	
	@PutMapping
	public ResponseEntity<RepBase> save(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(new RepBase(repository.save(usuario)));
	}
	
	@PostMapping
	public ResponseEntity<RepBase> update(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(new RepBase(repository.update(usuario)));
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll (SpringDataWebProperties.Pageable pageable) {
		return ResponseEntity.ok(repository.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable int id) {
		return ResponseEntity.ok(repository.findById(id));
	}

}
