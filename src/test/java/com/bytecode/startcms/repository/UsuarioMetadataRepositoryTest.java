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
import com.bytecode.startcms.model.UsuarioMetadata;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class UsuarioMetadataRepositoryTest {
    @Autowired
    private UsuarioMetadataRepository repository;

    @Test
    public void testA(){
        UsuarioMetadata MUsuarioMetadata = new UsuarioMetadata();
        MUsuarioMetadata.setClave("Edad");
        MUsuarioMetadata.setIdUsuario(2);
        MUsuarioMetadata.setTipo(Integer.class.getName());
        MUsuarioMetadata.setValor("18");
        MUsuarioMetadata.setIdUsuarioMetadata(1);

        Assert.assertTrue(repository.save(MUsuarioMetadata));
    }

    @Test
    public void testB(){
        UsuarioMetadata MUsuarioMetadata = new UsuarioMetadata();
        MUsuarioMetadata.setClave("Edad");
        MUsuarioMetadata.setIdUsuario(2);
        MUsuarioMetadata.setTipo(Integer.class.getName());
        MUsuarioMetadata.setValor("19");
        MUsuarioMetadata.setIdUsuarioMetadata(2);

        Assert.assertTrue(repository.update(MUsuarioMetadata));
    }

    @Test
    public void testC(){
        Assert.assertFalse(repository.findAll(new SpringDataWebProperties.Pageable()).isEmpty());
    }

    @Test
    public void testD(){
        Assert.assertTrue(repository.findById(2).getValor().equalsIgnoreCase("19"));
    }
}
