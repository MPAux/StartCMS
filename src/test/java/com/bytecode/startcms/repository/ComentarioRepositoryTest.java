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
import com.bytecode.startcms.model.Comentario;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class ComentarioRepositoryTest {
    @Autowired
    private ComentarioRepository repository;

    @Test
    public void testA(){
        Comentario MComentario = new Comentario();
        MComentario.setComentario("ComentarioA");
        MComentario.setIdPost(3);
        MComentario.setIdUsuario(2);
        //MComentario.setRespuesta(0);

        Assert.assertTrue(repository.save(MComentario));
    }

    @Test
    public void testB(){
        Comentario MComentario = new Comentario();
        MComentario.setComentario("ComentarioB");
        MComentario.setIdComentario(19);
        MComentario.setIdPost(3);
        MComentario.setIdUsuario(2);
        //MComentario.setRespuesta(0);

        Assert.assertTrue(repository.update(MComentario));
    }

    @Test
    public void testC(){
        Assert.assertFalse(repository.findAll(new SpringDataWebProperties.Pageable()).isEmpty());
    }

    @Test
    public void testD(){
        Assert.assertTrue(repository.findById(19).getComentario().equalsIgnoreCase("ComentarioB"));
    }
}