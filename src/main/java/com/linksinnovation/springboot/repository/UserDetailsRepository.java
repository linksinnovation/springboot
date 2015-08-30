package com.linksinnovation.springboot.repository;

import com.linksinnovation.springboot.domain.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
public interface UserDetailsRepository extends JpaRepository<Userdetails, String>{
    
}
