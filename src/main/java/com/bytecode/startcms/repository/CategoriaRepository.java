package com.bytecode.startcms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bytecode.startcms.model.Categoria;

@Repository
public class CategoriaRepository implements CategoriaRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean save(Categoria categoria) {
		String sql = String.format("insert into categoria(Nombre, Descripcion, CategoriaSuperior) values ('%s', '%s', '%d')", categoria.getNombre(), categoria.getDescripcion(), categoria.getCategoriaSuperior());
		try {
			jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			log.error("Hubo un problema salvando en la BD los datos de "+this.getClass());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Categoria categoria) {
		if(categoria.getIdCategoria() != 0) {
			String sql = String.format(
					"update categoria set Nombre='%s', Descripcion='%s', CategoriaSuperior = '%d' where IdCategoria = '%d'",
					categoria.getNombre(), categoria.getDescripcion(), categoria.getCategoriaSuperior(), categoria.getIdCategoria()
					);
			try {
				jdbcTemplate.execute(sql);
				return true;
			} catch (DataAccessException e) {
				log.error("Hubo un problema actualizando en la BD los datos de "+this.getClass()+" - Id: "+categoria.getIdCategoria());
				e.printStackTrace();
			}
		} else {
			log.error("La id de "+categoria.getClass()+" es 0");
		}
		return false;
	}

	@Override
	public List<Categoria> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
