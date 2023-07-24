package com.bytecode.startcms.repository;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.bytecode.startcms.component.TestDatabaseConfiguration;
import com.bytecode.startcms.model.Categoria;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class CategoriaRepositoryTest {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Test
	public void testInsert() {
		
		Categoria categoria = new Categoria();
		categoria.setNombre("Test1");
		categoria.setFecha(new Date());
		categoria.setDescripcion("Este es un ejemplo de categoria superior");
		categoria.setCategoriaSuperior(1);
		
		boolean result = categoriaRepository.save(categoria);
		Assert.assertTrue(result);
	}
}
