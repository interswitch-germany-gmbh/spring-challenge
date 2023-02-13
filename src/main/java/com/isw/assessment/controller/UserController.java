package com.isw.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isw.assessment.entity.UserEntity;
import com.isw.assessment.repository.UserRepository;

@RestController
@RequestMapping(path = {"/users"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserRepository userRepository;

  
    @GetMapping
    public List<UserEntity> getUsers() 
    {
        return userRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable("id") Integer id) 
    {
        return userRepository.findById(id).get();
    }
}