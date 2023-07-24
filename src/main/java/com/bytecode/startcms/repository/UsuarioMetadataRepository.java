package com.bytecode.startcms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.UsuarioMetadataMapper;
import com.bytecode.startcms.model.UsuarioMetadata;

@Repository
public class UsuarioMetadataRepository implements UsuarioMetadataRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	public List<UsuarioMetadata> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from usuario_metadata", new UsuarioMetadataMapper());
	}

	@Override
	public UsuarioMetadata findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from usuario_metadata where IdUsuarioMetadata = ?", new UsuarioMetadataMapper(), params);
	}
}
