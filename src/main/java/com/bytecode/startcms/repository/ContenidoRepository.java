package com.bytecode.startcms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.ContenidoMapper;
import com.bytecode.startcms.model.Contenido;

@Repository
public class ContenidoRepository implements ContenidoRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean save(Contenido object) {
		String sql = String.format("insert into contenido(Tipo, Contenido, IdPost) values ('%s', '%s', '%d')", object.getTipo(), object.getContenido(), object.getIdPost());
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
	public boolean update(Contenido object) {
		long id = object.getIdContenido();
		
		if(id > 0) {
			String sql = String.format(
					"update contenido set Tipo='%s', Contenido='%s', IdPost = '%d' where IdContenido = '%d'",
					object.getTipo(), object.getContenido(), object.getIdPost(), id
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
	public List<Contenido> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from contenido", new ContenidoMapper());
	}

	@Override
	public Contenido findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from contenido where IdContenido = ?", new ContenidoMapper(), params);
	}
}
