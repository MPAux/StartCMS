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
import com.bytecode.startcms.model.GrupoPermiso;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class GrupoPermisoRepositoryTest {
    @Autowired
    private GrupoPermisoRepository grupoPermisoRepository;

    @Test
    public void testA(){
        GrupoPermiso MGrupoPermiso = new GrupoPermiso();
        MGrupoPermiso.setIdGrupo(1);
        MGrupoPermiso.setIdPermiso(1);
        Assert.assertTrue(grupoPermisoRepository.save(MGrupoPermiso));
    }

    @Test
    public void testB(){
    	//This method should always return false as this table shouldn't be updated
        GrupoPermiso MGrupoPermiso = new GrupoPermiso();
        Assert.assertFalse(grupoPermisoRepository.update(MGrupoPermiso));
    }

    @Test
    public void testC(){
        Assert.assertFalse(grupoPermisoRepository.findAll(new SpringDataWebProperties.Pageable()).isEmpty());
    }

    @Test
    public void testD(){
    	//No hay ids de GrupoPermiso y tanto la id de grupo como la de permiso devolverían varios resultados
        //Assert.assertTrue(grupoPermisoRepository.findById(1).getIdPermiso()==1);
    	Assert.assertNull(grupoPermisoRepository.findById(1));
    }
}