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
import com.bytecode.startcms.mapper.PostMetadataMapper;
import com.bytecode.startcms.model.PostMetadata;

import jakarta.annotation.PostConstruct;

@Repository
public class PostMetadataRepository implements PostMetadataRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void postConstruct() {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean save(PostMetadata object) {
		String sql = String.format("insert into post_metadata(Clave, Valor, Tipo, IdPost) values ('%s', '%s', '%s', '%d')", object.getClave(), object.getValor(), object.getTipo(), object.getIdPost());
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
	public boolean update(PostMetadata object) {
		long id = object.getIdPostMetadata();
		
		if(id > 0) {
			String sql = String.format(
					"update post_metadata set Clave='%s', Valor='%s', Tipo = '%s', IdPost = '%d' where IdPostMetadata = '%d'",
					object.getClave(), object.getValor(), object.getTipo(), object.getIdPost(), id
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
	public List<PostMetadata> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from post_metadata", new PostMetadataMapper());
	}

	@Override
	public PostMetadata findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from post_metadata where IdPostMetadata = ?", new PostMetadataMapper(), params);
	}
	
	@Override
	public PostMetadata findByPostIdAndKey(int postId, String key) {
		Object[] params = {postId, key};
		log.info("PostId "+postId + "; key: "+key);
		return jdbcTemplate.queryForObject("select * from post_metadata where IdPost = ? and Clave like ?", new PostMetadataMapper(), params);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
