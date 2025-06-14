package com.example.demo.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor 
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name") 
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;
}