
package com.example.demo.service;

import com.example.demo.dto.UserRegistrationDTO;
import com.example.demo.model.User;

public interface UserService {
    User registerNewUser(UserRegistrationDTO registrationDTO);
    User findByUsername(String username);
}