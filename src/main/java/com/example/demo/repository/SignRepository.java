/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.repository;

import com.example.demo.model.SignModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LUIS CASANOVA
 */
@Repository
public interface SignRepository extends JpaRepository<SignModel, Long> {   
    Optional<SignModel> findById(Long id);
    SignModel save(SignModel signModel);
    void deleteById(Long id);
    List<SignModel> findAll();
    List<SignModel> findBySignName(String signName);
}
