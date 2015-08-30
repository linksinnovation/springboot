package com.linksinnovation.springboot.controller;

import com.linksinnovation.springboot.Application;
import com.linksinnovation.springboot.domain.Comment;
import com.linksinnovation.springboot.service.CommentService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class IndexRestControllerTest {
    
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private IndexRestController indexRestController;
    
    private MockMvc mockMvc;
    
    public IndexRestControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of get method, of class IndexRestController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGet() throws Exception {
        
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setComment("hahaha");
        comment.setAuthor("hehehehe");
        comments.add(comment);
        
        CommentService mock = Mockito.mock(CommentService.class);
        Mockito.when(mock.get()).thenReturn(comments);
        
        ReflectionTestUtils.setField(indexRestController, "commentService", mock);
        
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/rest"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        System.out.println(contentAsString);
    }

    /**
     * Test of save method, of class IndexRestController.
     */
    @Test
    public void testSave() {
    }
    
}
