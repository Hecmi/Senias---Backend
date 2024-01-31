/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.repository;

import com.example.demo.model.UserModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author LUIS CASANOVA
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findById(Long id);
    UserModel save(UserModel userModel);
    void deleteById(Long id);
    List<UserModel> findAll();
    List<UserModel> findByUserId(Long userId);
}
