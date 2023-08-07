package com.bytecode.startcms.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.PostMapper;
import com.bytecode.startcms.model.Post;

import jakarta.annotation.PostConstruct;

@Repository
public class PostRepository implements PostRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void postConstruct() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean save(Post object) {
		String sql = String.format("insert into post(Titulo, Slug, Extracto, IdUsuario, Categoria, ImagenDestacada, Tipo) values ('%s', '%s', '%s', '%d', '%d', '%s', '%s')", object.getTitulo(), object.getSlug(), object.getExtracto(), object.getIdUsuario(), object.getCategoria(), object.getImagenDestacada(), object.getTipo());
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
	public boolean update(Post object) {
		long id = object.getIdPost();
		
		if(id > 0) {
			String sql = String.format(
					"update post set Titulo='%s', Slug='%s', Extracto = '%s', IdUsuario = '%d', Categoria = '%d', ImagenDestacada = '%s', Tipo = '%s' where IdPost = '%d'",
					object.getTitulo(), object.getSlug(), object.getExtracto(), object.getIdUsuario(), object.getCategoria(), object.getImagenDestacada(), object.getTipo(), id
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
	public List<Post> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from post", new PostMapper());
	}
	
	@Override
	public Post findOnSave(Post object) {
		//Object[] parameters = {object.getTitulo(), object.getSlug(), object.getExtracto(), object.getIdUsuario(), object.getCategoria(), object.getImagenDestacada(), object.getTipo()};
		String sql = "insert into post(Titulo, Slug, Extracto, IdUsuario, Categoria, ImagenDestacada, Tipo) values (?, ?, ?, ?, ?, ?, ?)";
		int newId = 0;
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				  new PreparedStatementCreator() {
				    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				      ps.setString(1,object.getTitulo());
				      ps.setString(2,object.getSlug());
				      ps.setString(3,object.getExtracto());
				      ps.setLong(4,object.getIdUsuario());
				      ps.setLong(5, object.getCategoria());
				      ps.setString(6,object.getImagenDestacada());
				      ps.setString(7,object.getTipo());
				      return ps;
				    }
				  }, keyHolder);
		newId = keyHolder.getKey().intValue();
		
//		try {
//			//newId = jdbcTemplate.update(sql, parameters, Statement.RETURN_GENERATED_KEYS);
//			
//		} catch (DataAccessException e) {
//			log.error("Hubo un problema salvando en la BD los datos de la tabla "+this.getClass());
//			e.printStackTrace();
//			return null;
//		}
		return this.findById(newId);
	}

	@Override
	public Post findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from post where IdPost = ?", new PostMapper(), params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


}
