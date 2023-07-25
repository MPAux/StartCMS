package com.bytecode.startcms.repository;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.bytecode.startcms.mapper.PostMapper;
import com.bytecode.startcms.model.Post;

@Repository
public class PostRepository implements PostRep {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

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
	public List<Post> findAll(Pageable pageable) {
		return jdbcTemplate.query("select * from post", new PostMapper());
	}

	@Override
	public Post findById(int Id) {
		Object[] params = {Id};
		return jdbcTemplate.queryForObject("select * from post where IdPost = ?", new PostMapper(), params);
	}
}
