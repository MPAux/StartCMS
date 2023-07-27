package com.bytecode.startcms.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bytecode.startcms.component.TestDatabaseConfiguration;
import com.bytecode.startcms.model.Post;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestDatabaseConfiguration.class})
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;


    @Test
    public void insert() throws IOException {
        Post MPost = new Post();
        MPost.setIdPost(1);
        MPost.setImagenDestacada("image.jpg");
        MPost.setCategoria(1);
        MPost.setExtracto("Extracto de ejemplo");
        MPost.setSlug("nuevo-post");
        MPost.setTitulo("Nuevo Post");
        MPost.setTipo("Tipo 1");
        MPost.setIdUsuario(2);

        boolean result = postRepository.save(MPost);

        Assert.assertTrue(result);
    }

    @Test
    public void update() throws IOException {
        Post MPost = new Post();
        MPost.setIdPost(1);
        MPost.setImagenDestacada("image.jpg");
        MPost.setCategoria(1);
        MPost.setExtracto("Extracto de ejemplo");
        MPost.setSlug("nuevo-post-xd");
        MPost.setTitulo("Nuevo Post XD");
        MPost.setTipo("Tipo 1");
        MPost.setIdUsuario(2);

        boolean result = postRepository.update(MPost);

        Assert.assertTrue(result);
    }

    @Test
    public void findById(){
        Post MPost = postRepository.findById(1);
        Assert.assertNotNull(MPost);
    }

    @Test
    public void findAll(){
        SpringDataWebProperties.Pageable pageable = new SpringDataWebProperties.Pageable();
        Assert.assertFalse(postRepository.findAll(pageable).isEmpty());
    }
}