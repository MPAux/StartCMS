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

@Repository
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
		
		boolean tieneCategoriaSuperior = categoria.getCategoriaSuperior()>0;
		String sql = "insert into categoria(Nombre, Descripcion"
				+ (tieneCategoriaSuperior ? ", CategoriaSuperior" : "")
				+ ") values ('%s', '%s'"
				+ (tieneCategoriaSuperior ? ", '%d'" : "")
				+ ")";
		if(tieneCategoriaSuperior) {
			sql = String.format(sql, categoria.getNombre(), categoria.getDescripcion(), categoria.getCategoriaSuperior());
		} else {
			sql = String.format(sql, categoria.getNombre(), categoria.getDescripcion());
		}
		
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
			long id = categoria.getIdCategoria();
			boolean tieneCategoriaSuperior = categoria.getCategoriaSuperior() > 0;
			String sql = "update categoria set Nombre='%s', Descripcion='%s'"
					+ (tieneCategoriaSuperior ? ", CategoriaSuperior = '%d'" : "")
					+ " where IdCategoria = '%d'";
			if(tieneCategoriaSuperior) {
				sql = String.format(
						sql,
						categoria.getNombre(), categoria.getDescripcion(), categoria.getCategoriaSuperior(), id
						);
			} else {
				sql = String.format(
						sql,
						categoria.getNombre(), categoria.getDescripcion(), id
						);	
			}
			
			try {
				int rowsAffected = jdbcTemplate.update(sql);
				if(rowsAffected > 0) {
					return true;
				}
				log.error("No se actualizó ninguna fila con la id "+id);
			} catch (DataAccessException e) {
				log.error("Hubo un problema actualizando en la BD los datos de la tabla "+this.getClass()+" - Id: "+id);
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
