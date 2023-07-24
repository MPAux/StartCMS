package com.bytecode.startcms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.PermisoMapper;
import com.bytecode.startcms.model.Permiso;

@Repository
public class PermisoRepository implements PermisoRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	public List<Permiso> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from permiso", new PermisoMapper());
	}

	@Override
	public Permiso findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from permiso where IdPermiso = ?", new PermisoMapper(), params);
	}
}
