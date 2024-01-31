/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="User_account")
public class UserModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false)
    @Getter @Setter
    Long userId;
     
    @Column(name="names", nullable = false, length = 100)
    @Getter @Setter
    String names;
    
    @Column(name="second_names", nullable = false, length = 100)
    @Getter @Setter
    String secondNames;
    
    @Column(name="username", nullable = false, length = 50)
    @Getter @Setter
    String username;
    
    @Column(name="password", nullable = false, length = 15)
    @Getter @Setter
    String password;
    
    @Column(name="type", nullable = false, length = 3)
    @Getter @Setter
    String type;
    
    @Column(name="status", nullable = false, length = 3)
    @Getter @Setter
    String status;    
    
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
