package com.bytecode.startcms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.PostMetadataMapper;
import com.bytecode.startcms.model.PostMetadata;

@Repository
public class PostMetadataRepository implements PostMetadataRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	public List<PostMetadata> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from post_metadata", new PostMetadataMapper());
	}

	@Override
	public PostMetadata findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from post_metadata where IdPostMetadata = ?", new PostMetadataMapper(), params);
	}
}
