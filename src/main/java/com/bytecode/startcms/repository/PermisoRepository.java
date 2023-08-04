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
import com.bytecode.startcms.mapper.PermisoMapper;
import com.bytecode.startcms.model.Permiso;

import jakarta.annotation.PostConstruct;

@Repository
public class PermisoRepository implements PermisoRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void postConstruct() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean save(Permiso object) {
		String sql = String.format("insert into permiso(Nombre) values ('%s')", object.getNombre());
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
	public boolean update(Permiso object) {
		long id = object.getIdPermiso();
		
		if(id > 0) {
			String sql = String.format(
					"update permiso set Nombre='%s' where IdPermiso = '%d'",
					object.getNombre(), id
					);
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
			log.error("La id de "+object.getClass()+" no es válida");
		}
		return false;
	}

	@Override
	public List<Permiso> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from permiso", new PermisoMapper());
	}

	@Override
	public Permiso findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from permiso where IdPermiso = ?", new PermisoMapper(), params);
	}
	
	@Override
	public List<Permiso> findByIdGrupo(int idGrupo) {
		Object[] params = {idGrupo};
		// SELECT p.* FROM grupo_permiso gp INNER JOIN permiso p ON gp.IdPermiso = p.IdPermiso WHERE gp.IdGrupo = ?;
		return jdbcTemplate.query("SELECT p.* FROM grupo_permiso gp INNER JOIN permiso p ON gp.IdPermiso = p.IdPermiso WHERE gp.IdGrupo = ?", new PermisoMapper(), params);
	}
	
	@Override
	public List<Permiso> findByNotIdGrupo(int idGrupo) {
		Object[] params = {idGrupo};
		return jdbcTemplate.query("SELECT * FROM permiso p WHERE IdPermiso NOT IN (SELECT IdPermiso FROM grupo_permiso WHERE IdGrupo = ?)", new PermisoMapper(), params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}



}
