package com.bytecode.startcms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.ComentarioMapper;
import com.bytecode.startcms.model.Comentario;

@Repository
public class ComentarioRepository implements ComentarioRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean save(Comentario object) {
		String sql = String.format("insert into comentario(Comentario, IdPost, IdUsuario, Respuesta) values ('%s', '%d', '%d', '%d')", object.getComentario(), object.getIdPost(), object.getIdUsuario(), object.getRespuesta());
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
	public boolean update(Comentario object) {
		long id = object.getIdComentario();
		
		if(id > 0) {
			String sql = String.format(
					"update categoria set Comentario='%s', IdPost='%d', IdUsuario = '%d',Respuesta = '%d' where IdComentario = '%d'",
					object.getComentario(), object.getIdPost(), object.getIdUsuario(), object.getRespuesta(), id
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
	public List<Comentario> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from comentario", new ComentarioMapper());
	}

	@Override
	public Comentario findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from comentario where IdComentario = ?", new ComentarioMapper(), params);
	}
}
