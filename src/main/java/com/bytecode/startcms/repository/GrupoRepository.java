package com.bytecode.startcms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.GrupoMapper;
import com.bytecode.startcms.model.Grupo;

@Repository
public class GrupoRepository implements GrupoRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean save(Grupo object) {
		String sql = String.format("insert into grupo(Nombre) values ('%s')", object.getNombre());
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
	public boolean update(Grupo object) {
		long id = object.getIdGrupo();
		
		if(id > 0) {
			String sql = String.format(
					"update grupo set Nombre='%s' where IdGrupo = '%d'",
					object.getNombre(), id
					);
			try {
				jdbcTemplate.execute(sql);
				return true;
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
	public List<Grupo> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from grupo", new GrupoMapper());
	}

	@Override
	public Grupo findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from grupo where IdGrupo = ?", new GrupoMapper(), params);
	}
}
