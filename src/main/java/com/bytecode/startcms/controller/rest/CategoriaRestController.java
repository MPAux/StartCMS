package com.bytecode.startcms.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytecode.startcms.model.Categoria;
import com.bytecode.startcms.model.common.RepBase;
import com.bytecode.startcms.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaRestController {
	@Autowired
	private CategoriaRepository repository;
	
	@PutMapping
	@CacheEvict(value = "categorias", allEntries = true)
	public ResponseEntity<RepBase> save(@RequestBody Categoria categoria) {
		return ResponseEntity.ok(new RepBase(repository.save(categoria)));
	}
	
	@PostMapping
	@CacheEvict(value = "categorias", allEntries = true)
	public ResponseEntity<RepBase> update(@RequestBody Categoria categoria) {
		return ResponseEntity.ok(new RepBase(repository.update(categoria)));
	}
	
	@GetMapping
	@Cacheable(value = "categorias")
	public ResponseEntity<List<Categoria>> findAll (SpringDataWebProperties.Pageable pageable) {
		return ResponseEntity.ok(repository.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	@Cacheable(value = "categorias")
	public ResponseEntity<Categoria> findById(@PathVariable int id) {
		return ResponseEntity.ok(repository.findById(id));
	}

}
