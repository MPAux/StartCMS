package com.bytecode.startcms.repository;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.bytecode.startcms.component.TestDatabaseConfiguration;
import com.bytecode.startcms.model.Categoria;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class CategoriaRepositoryTest {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Test
	public void testInsert() {
		log.info("Inside Test Insert");
		Categoria categoria = new Categoria();
		categoria.setNombre("Test2");
		categoria.setFecha(new Date());
		categoria.setDescripcion("Este es un ejemplo de categoria superior");
		categoria.setCategoriaSuperior(1);
		
		boolean result = categoriaRepository.save(categoria);
		log.info("Result value: "+result);

		Assert.assertTrue(result);
	}
	
	@Test()
	public void testUpdate() {
		Categoria categoria = new Categoria();
		categoria.setIdCategoria(2);
		categoria.setNombre("testUpdate2");
		categoria.setDescripcion("Nueva descripcion (update)");
		categoria.setCategoriaSuperior(1);
		
		boolean result = categoriaRepository.update(categoria);
		
		Assert.assertTrue(result);
	}
	
	@Test
	public void testFindById() {
		Categoria categoria = categoriaRepository.findById(2);
		Assert.assertNotNull(categoria);
		Assert.assertTrue("testUpdate2".equals(categoria.getNombre()));
	}
	
	@Test
	public void testFindAll() {
		SpringDataWebProperties.Pageable pageable = new SpringDataWebProperties.Pageable();
		Assert.assertFalse(categoriaRepository.findAll(pageable).isEmpty());
	}
}
