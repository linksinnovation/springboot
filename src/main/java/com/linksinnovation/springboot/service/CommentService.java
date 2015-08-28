/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linksinnovation.springboot.service;

import com.linksinnovation.springboot.dto.Comment;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Service
public class CommentService {
    
    private static List<Comment> comments = new ArrayList<>();
    
    public List<Comment> get(){
        return comments;
    }
    
    public List<Comment> save(Comment comment){
        comments.add(comment);
        return comments;
    }
    
}
