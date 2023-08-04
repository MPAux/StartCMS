package com.bytecode.startcms.repository;

import java.util.List;

import com.bytecode.startcms.model.Permiso;

public interface PermisoRep extends BaseRep<Permiso> {
	public List<Permiso> findByIdGrupo(int idGrupo);
	public List<Permiso> findByNotIdGrupo(int idGrupo);
}
