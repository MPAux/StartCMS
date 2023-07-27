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
import com.bytecode.startcms.model.PostMetadata;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class PostMetadataRepositoryTest {
    @Autowired
    private PostMetadataRepository repository;

    @Test
    public void testA(){
        PostMetadata MPostMetadata = new PostMetadata();
        MPostMetadata.setClave("Visitas");
        MPostMetadata.setIdPost(1);
        MPostMetadata.setTipo(Integer.class.getName());
        MPostMetadata.setValor("13");
        MPostMetadata.setIdPostMetadata(1);

        Assert.assertTrue(repository.save(MPostMetadata));
    }

    @Test
    public void testB(){
        PostMetadata MPostMetadata = new PostMetadata();
        MPostMetadata.setClave("Visitas");
        MPostMetadata.setIdPost(1);
        MPostMetadata.setTipo(Integer.class.getName());
        MPostMetadata.setValor("18");
        MPostMetadata.setIdPostMetadata(1);

        Assert.assertTrue(repository.update(MPostMetadata));
    }

    @Test
    public void testC(){
        Assert.assertFalse(repository.findAll(new SpringDataWebProperties.Pageable()).isEmpty());
    }

    @Test
    public void testD(){
        Assert.assertTrue(repository.findById(1).getValor().equalsIgnoreCase("18"));
    }
}