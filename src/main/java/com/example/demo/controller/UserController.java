/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.model.SignModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LUIS CASANOVA
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired    
    private UserService userService;
    
    @GetMapping("/get/{userId}")
    public Optional<UserModel> getAll(@PathVariable Long userId){
        return this.userService.getUserById(userId);
    }
    
}
