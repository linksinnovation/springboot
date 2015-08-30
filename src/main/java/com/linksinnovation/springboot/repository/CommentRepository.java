package com.linksinnovation.springboot.repository;

import com.linksinnovation.springboot.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    @Query("SELECT c FROM Comment c WHERE c.comment LIKE %?1%")
    public List<Comment> searchComment(String comment);
}
