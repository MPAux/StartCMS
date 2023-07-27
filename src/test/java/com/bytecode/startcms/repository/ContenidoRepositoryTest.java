package com.bytecode.startcms.repository;


import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bytecode.startcms.component.TestDatabaseConfiguration;
import com.bytecode.startcms.model.Contenido;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class ContenidoRepositoryTest {

    @Autowired
    private ContenidoRepository repository;

    @Test
    public void testA(){
        Contenido MContenido = new Contenido();
        MContenido.setContenido("Hola");
        MContenido.setIdPost(3);
        MContenido.setTipo(String.class.getName());
        //MContenido.setIdContenido(1);

        Assert.assertTrue(repository.save(MContenido));
    }

    @Test
    public void testB(){
        Contenido MContenido = new Contenido();
        MContenido.setContenido("HolaAA");
        MContenido.setIdPost(3);
        MContenido.setTipo(String.class.getName());
        MContenido.setIdContenido(3);

        Assert.assertTrue(repository.update(MContenido));
    }

    @Test
    public void testC(){
        Assert.assertFalse(repository.findAll(new SpringDataWebProperties.Pageable()).isEmpty());
    }

    @Test
    public void testD(){
        Assert.assertTrue(repository.findById(3).getContenido().equalsIgnoreCase("HolaAA"));
    }
}
