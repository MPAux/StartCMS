package com.bytecode.startcms.repository;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.CategoriaMapper;

import com.bytecode.startcms.model.Categoria;

import jakarta.annotation.PostConstruct;

//@Repository
public class CategoriaRepository implements CategoriaRep {
	Log log = LogFactory.getLog(getClass());
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void postConstruct() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean save(Categoria categoria) {
		log.info("Inside CategoriaRepository save method");
		String sql = String.format("insert into categoria(Nombre, Descripcion, CategoriaSuperior) values ('%s', '%s', '%d')", categoria.getNombre(), categoria.getDescripcion(), categoria.getCategoriaSuperior());
		try {
			jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			log.error("Hubo un problema salvando en la BD los datos de la tabla "+this.getClass());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Categoria categoria) {
		if(categoria.getIdCategoria() > 0) {
			String sql = String.format(
					"update categoria set Nombre='%s', Descripcion='%s', CategoriaSuperior = '%d' where IdCategoria = '%d'",
					categoria.getNombre(), categoria.getDescripcion(), categoria.getCategoriaSuperior(), categoria.getIdCategoria()
					);
			try {
				jdbcTemplate.execute(sql);
				return true;
			} catch (DataAccessException e) {
				log.error("Hubo un problema actualizando en la BD los datos de la tabla "+this.getClass()+" - Id: "+categoria.getIdCategoria());
				e.printStackTrace();
			}
		} else {
			log.error("La id de "+categoria.getClass()+" no es válida");
		}
		return false;
	}

	@Override
	public List<Categoria> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from categoria", new CategoriaMapper());
	}

	@Override
	public Categoria findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from categoria where IdCategoria = ?", new CategoriaMapper(), params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
