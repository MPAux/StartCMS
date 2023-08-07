package com.bytecode.startcms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bytecode.startcms.model.Post;

public class PostMapper implements RowMapper<Post> {

	@Override
	public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
		Post post = new Post();
		post.setIdPost(rs.getInt("IdPost"));
		post.setTitulo(rs.getString("Titulo"));
		post.setSlug(rs.getString("Slug"));
		post.setExtracto(rs.getString("Extracto"));
		post.setIdUsuario(rs.getInt("IdUsuario"));
		post.setCategoria(rs.getInt("Categoria"));
		post.setImagenDestacada(rs.getString("ImagenDestacada"));
		post.setTipo(rs.getString("Tipo"));
		post.setFecha(rs.getDate("Fecha"));
		return post;
	}

}
