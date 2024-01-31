/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.service;

import com.example.demo.dto.SignDto;
import com.example.demo.model.SignModel;
import com.example.demo.model.UserModel;
import com.example.demo.repository.SignRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 *
 * @author LUIS CASANOVA
 */
@Service
@Validated
public class SignService {
    private SignRepository signRepository;
    private UserRepository userRepository;
    
    @Autowired
    public SignService(SignRepository signRepository, UserRepository userRepository){
        this.signRepository = signRepository;
        this.userRepository = userRepository;
    }
    
    public List<SignModel> listSigns(){
        return this.signRepository.findAll();
    }

    public ResponseEntity<SignModel> addSign(SignDto signDto){ 
        //Transformar el Id ingrsado en tipo Long a un usario existente
        UserModel userModel = userRepository.findById((long) signDto.getUserId()).get();
        
        //Crear el modelo base y definir el Id del usuario
        SignModel signModel = new SignModel();
        signModel.setUserId(userModel);
        
        //Guardar la seña transformando el dto a SignModel y guardarlo
        SignModel savedSign = this.signRepository.save(SignDto.toModel(signDto, signModel));
        return new ResponseEntity<>(savedSign, HttpStatus.CREATED);
    } 
    
    public ResponseEntity<SignModel> updateSign(Long signId, SignDto signDto){       
        //Obtener el id de la seña a la que corresponda el id ingresado
        SignModel signModel = signRepository.findById((long) signId).get();
        
        //Convertir dle dto a modelo y guardarlo para su actualización
        SignModel updatedSign = this.signRepository.save(SignDto.toModel(signDto, signModel));
        return new ResponseEntity<>(updatedSign, HttpStatus.OK);
    } 
}
