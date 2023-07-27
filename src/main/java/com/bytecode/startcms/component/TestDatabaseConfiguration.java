package com.bytecode.startcms.component;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bytecode.startcms.repository.CategoriaRepository;
import com.bytecode.startcms.repository.ComentarioRepository;
import com.bytecode.startcms.repository.ContenidoRepository;
import com.bytecode.startcms.repository.GrupoPermisoRepository;
import com.bytecode.startcms.repository.GrupoRepository;
import com.bytecode.startcms.repository.PermisoRepository;
import com.bytecode.startcms.repository.PostMetadataRepository;
import com.bytecode.startcms.repository.PostRepository;
import com.bytecode.startcms.repository.UsuarioMetadataRepository;
import com.bytecode.startcms.repository.UsuarioRepository;

@Configuration
public class TestDatabaseConfiguration {
    @Bean
    public DataSource getDataSource() {
	
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost/test_blog");
        dataSourceBuilder.username("bytecode");
        dataSourceBuilder.password("root1234");
        return dataSourceBuilder.build();      
	}

    @Bean
    public CategoriaRepository categoriaRepository() {
		return new CategoriaRepository();
	}
    
    @Bean
    public UsuarioRepository usuarioRepository(){
        return new UsuarioRepository();
    }

    @Bean
    public PostRepository postRepository(){return new PostRepository();}

    @Bean
    public UsuarioMetadataRepository usuarioMetadataRepository(){
        return new UsuarioMetadataRepository();
    }

    @Bean
    public PostMetadataRepository postMetadataRepository(){
        return new PostMetadataRepository();
    }

    @Bean
    public PermisoRepository permisoRepository(){
        return new PermisoRepository();
    }

    @Bean
    public GrupoRepository grupoRepository(){
        return new GrupoRepository();
    }

    @Bean
    public ContenidoRepository contenidoRepository(){
        return new ContenidoRepository();
    }

    @Bean
    public ComentarioRepository comentarioRepository(){
        return new ComentarioRepository();
    }

    @Bean
    public GrupoPermisoRepository grupoPermisoRepository(){
        return new GrupoPermisoRepository();
    }
}
