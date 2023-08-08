package com.bytecode.startcms.repository;

import com.bytecode.startcms.model.Usuario;

public interface UsuarioRep extends BaseRep<Usuario>{
	public Usuario findByCorreo(String correo);
}
