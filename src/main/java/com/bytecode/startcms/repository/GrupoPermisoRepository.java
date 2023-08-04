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
import com.bytecode.startcms.mapper.GrupoPermisoMapper;
import com.bytecode.startcms.model.GrupoPermiso;

import jakarta.annotation.PostConstruct;

@Repository
public class GrupoPermisoRepository implements GrupoPermisoRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void postConstruct() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean save(GrupoPermiso object) {
		String sql = String.format("insert into grupo_permiso(IdGrupo, IdPermiso) values ('%d', '%d')", object.getIdGrupo(), object.getIdPermiso());
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
	public boolean update(GrupoPermiso object) {
		log.info("Invalid method update for "+object.getClass());
		return false;
	}

	@Override
	public List<GrupoPermiso> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from grupo_permiso", new GrupoPermisoMapper());
	}

	@Override
	public GrupoPermiso findById(int id) {
		//Check this method id
//		Object[] params = {Id};
//		log.info("Getting GrupoPermiso by IdGrupo: "+Id);
//		return jdbcTemplate.queryForObject("select * from grupo_permiso where IdGrupo = ?", new GrupoPermisoMapper(), params);
		GrupoPermiso grupoPermiso = null;
		return grupoPermiso;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
