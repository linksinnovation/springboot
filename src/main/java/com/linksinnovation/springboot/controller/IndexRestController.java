package com.linksinnovation.springboot.controller;

import com.linksinnovation.springboot.domain.Comment;
import com.linksinnovation.springboot.service.CommentService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */

@RestController
@RequestMapping("/rest")
public class IndexRestController {
    
    @Autowired
    private CommentService commentService;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Comment> get(){
        return commentService.get();
    }
    
    @Secured("ROLE_ADMIN")
    @RequestMapping(method = RequestMethod.POST)
    public List<Comment> save(@Valid @RequestBody Comment comment){
        return commentService.save(comment);
    }
    
}
