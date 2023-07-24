package com.bytecode.startcms.component;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bytecode.startcms.repository.CategoriaRepository;

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
}
