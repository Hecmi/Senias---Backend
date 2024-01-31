/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LUIS CASANOVA
 */
@RestController
@RequestMapping("/api")
public class Prueba {
    public static void main(String[] args) {
        UserService userService = new UserService(); 
        UserModel userModel = userService.getUserById((long)1).get();
        System.out.println(userModel.getUserId());
        
    }
}