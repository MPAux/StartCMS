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
import com.bytecode.startcms.model.Grupo;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class GrupoRepositoryTest {

    @Autowired
    private GrupoRepository grupoRepository;

    @Test
    public void testA(){
        Grupo MGrupo = new Grupo();
        MGrupo.setIdGrupo(1);
        MGrupo.setNombre("Grupo1");

        Assert.assertTrue(grupoRepository.save(MGrupo));
    }

    @Test
    public void testB(){
        Grupo MGrupo = new Grupo();
        MGrupo.setIdGrupo(1);
        MGrupo.setNombre("Grupo2");

        Assert.assertTrue(grupoRepository.update(MGrupo));
    }

    @Test
    public void testC(){
        Assert.assertFalse(grupoRepository.findAll(new SpringDataWebProperties.Pageable()).isEmpty());
    }

    @Test
    public void testD(){
        Assert.assertTrue(grupoRepository.findById(1).getNombre().equalsIgnoreCase("Grupo2"));
    }

}