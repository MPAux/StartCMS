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
import com.bytecode.startcms.mapper.UsuarioMapper;
import com.bytecode.startcms.model.Usuario;

import jakarta.annotation.PostConstruct;

@Repository
public class UsuarioRepository implements UsuarioRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void postConstruct() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean save(Usuario object) {
		String sql = String.format("insert into usuario(Nombre, Apellido, Contrasena, Correo, IdGrupo) values ('%s', '%s', '%s', '%s', '%d')", object.getNombre(), object.getApellido(), object.getContrasena(), object.getCorreo(), object.getIdGrupo());
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
	public boolean update(Usuario object) {
		long id = object.getIdUsuario();
		
		if(id > 0) {
			String sql = String.format(
					"update usuario set Nombre='%s', Apellido='%s', Contrasena = '%s', Correo = '%s', IdGrupo = '%d' where IdUsuario = '%d'",
					object.getNombre(), object.getApellido(), object.getContrasena(), object.getCorreo(), object.getIdGrupo(), id
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
	public List<Usuario> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from usuario", new UsuarioMapper());
	}

	@Override
	public Usuario findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from usuario where IdUsuario = ?", new UsuarioMapper(), params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
