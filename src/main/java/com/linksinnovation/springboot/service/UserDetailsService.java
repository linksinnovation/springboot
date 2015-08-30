package com.linksinnovation.springboot.service;

import com.linksinnovation.springboot.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jirawong Wongdokpuang <greannetwork@gmail.com>
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        return userDetailsRepository.findOne(string);   
    }
    
}
