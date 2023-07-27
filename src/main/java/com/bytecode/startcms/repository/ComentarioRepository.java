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
import com.bytecode.startcms.mapper.ComentarioMapper;
import com.bytecode.startcms.model.Comentario;

import jakarta.annotation.PostConstruct;

@Repository
public class ComentarioRepository implements ComentarioRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void postConstruct() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean save(Comentario object) {

		boolean esRespuesta = object.getRespuesta()>0;
		String sql = "insert into comentario(Comentario, IdPost, IdUsuario"
		+(esRespuesta ? ", Respuesta" : "")
		+") values ('%s', '%d', '%d'"
		+(esRespuesta ? ", '%d'" : "")
		+")";		
		
		if(esRespuesta) {
			sql = String.format(sql, object.getComentario(), object.getIdPost(), object.getIdUsuario(), object.getRespuesta());
		} else {
			sql = String.format(sql, object.getComentario(), object.getIdPost(), object.getIdUsuario());
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
	public boolean update(Comentario object) {
		long id = object.getIdComentario();
		
		if(id > 0) {

			boolean esRespuesta = object.getRespuesta()>0;
			String sql = "update comentario set Comentario='%s', IdPost='%d', IdUsuario = '%d'"
			+(esRespuesta ? ", Respuesta = '%d'" : "")
			+" where IdComentario = %d";
			
			if(esRespuesta) {
				sql = String.format(
						sql,
						object.getComentario(), object.getIdPost(), object.getIdUsuario(), object.getRespuesta(), id
						);
			} else {
				sql = String.format(
						sql,
						object.getComentario(), object.getIdPost(), object.getIdUsuario(), id
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
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
