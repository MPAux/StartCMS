package com.bytecode.startcms.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.bytecode.startcms.model.Categoria;

public interface CategoriaRep {
	public boolean save(Categoria categoria);
	public boolean update(Categoria categoria);
	public List<Categoria> findAll(Pageable pageable);
}

