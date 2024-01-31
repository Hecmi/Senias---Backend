/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;


import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author LUIS CASANOVA
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
        

    public List<UserModel> getAll(){
        return this.userRepository.findAll();
    }
    
    public Optional<UserModel> getUserById(Long id){
        Optional<UserModel> userModel = this.userRepository.findById(id);
        return userModel;
    }
}
