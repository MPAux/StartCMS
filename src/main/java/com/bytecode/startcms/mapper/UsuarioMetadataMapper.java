package com.bytecode.startcms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bytecode.startcms.model.UsuarioMetadata;

public class UsuarioMetadataMapper implements RowMapper<UsuarioMetadata>{

	@Override
	public UsuarioMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {
		UsuarioMetadata usuarioMetadata = new UsuarioMetadata();
		usuarioMetadata.setIdUsuarioMetadata(rs.getInt("IdUsuarioMetadata"));
		usuarioMetadata.setIdUsuario(rs.getInt("IdUsuario"));
		usuarioMetadata.setClave(rs.getString("Clave"));
		usuarioMetadata.setValor(rs.getString("Valor"));
		usuarioMetadata.setTipo(rs.getString("Tipo"));
		return usuarioMetadata;
	}

}
