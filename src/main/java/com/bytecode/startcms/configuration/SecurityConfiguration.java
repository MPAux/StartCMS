//package com.bytecode.startcms.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.bytecode.startcms.service.UsuarioService;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//
////@EnableWebSecurity
////@Configuration
//public class SecurityConfiguration {
//	@Autowired
//	private UsuarioService usuarioService;
//	
//	String[] resources = new String[] {
//		"/include/**", "/css/**", "/icons/**", "/img/**", "/js/**", "/vendor/**" 
//	};
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////            .authorizeHttpRequests((authz) -> authz
////                .anyRequest().authenticated()
////            )
////            .httpBasic(withDefaults());
//        http
//        .authorizeHttpRequests((authz) -> {
//			try {
//				authz
//				    .requestMatchers(resources).permitAll()
//				    .and()
//				    .authorizeRequests()
//				    .anyRequest()
//				    .authenticated()
//				    .and()
//				    .formLogin()
//				    .loginPage("/login")
//				    .failureForwardUrl("/login?error=true")
//				    .successForwardUrl("/admin");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//        );
//    return http.build();
//
//    }
//    
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {	
//    	return new BCryptPasswordEncoder();	
//    }
//    
////    @Bean
////    public InMemoryUserDetailsManager userDetailsService() {
////    	return new InMemoryUserDetailsManager(usuarioService.loadUserByUsername(null));
////    }
//}
