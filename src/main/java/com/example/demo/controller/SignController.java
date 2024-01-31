/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.dto.SignDto;
import com.example.demo.model.SignModel;
import com.example.demo.service.SignService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LUIS CASANOVA
 */
@RestController
@RequestMapping("/sign")
public class SignController {
    
    @Autowired    
    private SignService signService;
    
    @GetMapping("/list")
    public List<SignModel> getAll() {
        return signService.listSigns();
    }
    
    @PostMapping("/add")
    public ResponseEntity<SignModel> addSign(@Valid @RequestBody SignDto signDto) {
        ResponseEntity<SignModel> savedSign = signService.addSign(signDto);
        return savedSign;
    }
    
    @PutMapping("/update/{signId}")
    public ResponseEntity<SignModel> updateSign(@PathVariable Long signId, @Valid @RequestBody SignDto signDto) {
        ResponseEntity<SignModel> updatedSign = signService.updateSign(signId, signDto);
        return updatedSign;
    }
    
    
    
}
