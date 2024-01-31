/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.apimanagement;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author LUIS CASANOVA
 */
@Data
@RestController
public class ApiResponse implements Serializable {
    private Integer httpStatus;
    private Integer codeStatus;
    private String data;
    private LocalDateTime date;
    
    public ApiResponse getResponse(){
        this.setDate(LocalDateTime.now());
        return this;
    }
}
