/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.dto;

import com.example.demo.exceptionhandler.Messages;
import com.example.demo.model.SignModel;
import com.example.demo.model.UserModel;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
/**
 *
 * @author LUIS CASANOVA
 */
@Data
public class SignDto {
    private Long signId;
    private Long userId;
    
    @NotBlank(message = "Name " + Messages.VALIDATION_NO_BLANK)
    @NotNull(message = "Name " + Messages.VALIDATION_NO_NULL)
    @Size(min = 1, max = 50, message = "Name " + Messages.VALIDATION_RANGE_LENGTH)
    private String signName;
    
    @NotBlank(message = "Type " + Messages.VALIDATION_NO_BLANK)
    @NotNull(message = "Type " + Messages.VALIDATION_NO_NULL)
    @Size(min = 3, max = 3, message = "Type " + Messages.VALIDATION_MINMAX_LENGTH)
    private String type;
    
    @NotBlank(message = "Status " + Messages.VALIDATION_NO_BLANK)
    @NotNull(message = "Status " + Messages.VALIDATION_NO_NULL)
    @Size(min = 3, max = 3, message = "Status " + Messages.VALIDATION_MINMAX_LENGTH)
    private String status;
    
    @NotBlank(message = "Text to be readed " + Messages.VALIDATION_NO_BLANK)
    @NotNull(message = "Text to be readed " + Messages.VALIDATION_NO_NULL)
    @Size(min = 1, max = 50, message = "Text to be readed " + Messages.VALIDATION_RANGE_LENGTH)
    private String tts;
    
    private LocalDateTime  creationDate;
    private LocalDateTime  modificationDate;
   
    public static SignModel toModel(SignDto signDto, SignModel signModel){
        signModel.setSignName(signDto.getSignName());
        signModel.setType(signDto.getType());
        signModel.setStatus(signDto.getStatus());
        signModel.setTts(signDto.getTts());
        
        return signModel;
    }
}
