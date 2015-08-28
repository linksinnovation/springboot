/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linksinnovation.springboot.controller;

import com.linksinnovation.springboot.dto.Comment;
import com.linksinnovation.springboot.service.CommentService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Controller
@RequestMapping("/")
public class IndexController {
    
    @Autowired
    private CommentService commentService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String get(Model model){
        model.addAttribute("comments", commentService.get());
        model.addAttribute("formComment", new Comment());
        return "index";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("formComment") Comment comment, BindingResult result,Model model){
        if(result.hasErrors()){
            return "index";
        }
        List<Comment> save = commentService.save(comment);
        model.addAttribute("comments", save);
        model.addAttribute("formComment", new Comment());
        return "index";
    }
}
