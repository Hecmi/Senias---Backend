/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LUIS CASANOVA
 */
@Entity
@Table(name="Sign")
public class SignModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sign_id", nullable = false)
    @Getter @Setter
    private Long signId;
    
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @Getter @Setter
    private UserModel userId;
    
    @Column(name="sign_name", nullable = false, length = 50)
    @Getter @Setter
    private String signName;
    
    @Column(name="type", nullable = false, length = 3)
    @Getter @Setter
    private String type;
    
    @Column(name="status", nullable = false, length = 3)
    @Getter @Setter
    private String status;
    
    @Column(name="tts", nullable = false, length = 50)
    @Getter @Setter
    private String tts;
    
    @Column(name="creation_date", nullable = false)
    @Getter
    private LocalDateTime  creationDate;
    
    @Column(name="modification_date", nullable = false)
    @Getter
    private LocalDateTime  modificationDate;
    
    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
        modificationDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modificationDate = LocalDateTime.now();
    }
}

