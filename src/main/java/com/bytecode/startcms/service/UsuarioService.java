//package com.bytecode.startcms.service;
//
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.bytecode.startcms.model.Grupo;
//import com.bytecode.startcms.model.Usuario;
//import com.bytecode.startcms.repository.GrupoRepository;
//import com.bytecode.startcms.repository.UsuarioRepository;
//
////@Service
//public class UsuarioService implements UserDetailsService {
//	@Autowired
//	private UsuarioRepository usuarioRepository;
//	@Autowired
//	private GrupoRepository grupoRepository;
//	
//	@Override
//	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
//		Usuario usuario = usuarioRepository.findByCorreo(correo);
//		Grupo grupo = grupoRepository.findById((int) usuario.getIdGrupo());
//		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+grupo.getNombre().toUpperCase());
//		
//		return new User(usuario.getCorreo(),usuario.getContrasena(), Arrays.asList(grantedAuthority));
//	}
//
//}
