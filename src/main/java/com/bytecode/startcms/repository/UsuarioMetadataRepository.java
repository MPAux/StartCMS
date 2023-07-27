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
import com.bytecode.startcms.mapper.UsuarioMetadataMapper;
import com.bytecode.startcms.model.UsuarioMetadata;

import jakarta.annotation.PostConstruct;

@Repository
public class UsuarioMetadataRepository implements UsuarioMetadataRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void postConstruct() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean save(UsuarioMetadata object) {
		String sql = String.format("insert into usuario_metadata(IdUsuario, Clave, Valor, Tipo) values ('%d', '%s', '%s', '%s')", object.getIdUsuario(), object.getClave(), object.getValor(), object.getTipo());
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
	public boolean update(UsuarioMetadata object) {
		long id = object.getIdUsuarioMetadata();
		
		if(id > 0) {
			String sql = String.format(
					"update usuario_metadata set IdUsuario='%d', Clave='%s', Valor = '%s', Tipo = '%s' where IdUsuarioMetadata = '%d'",
					object.getIdUsuario(), object.getClave(), object.getValor(), object.getTipo(), id
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
	public List<UsuarioMetadata> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from usuario_metadata", new UsuarioMetadataMapper());
	}

	@Override
	public UsuarioMetadata findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from usuario_metadata where IdUsuarioMetadata = ?", new UsuarioMetadataMapper(), params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
