package com.shayan.journalyt.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shayan.journalyt.entity.User;
import com.shayan.journalyt.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user: " + e.getMessage());
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(ObjectId id) {
        try {
            return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get user by id: " + e.getMessage());
        }
    }

    public User findByUsername(String username){
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find user by username: " + e.getMessage());
        }
    }

    public void deleteUser(ObjectId id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage());
        }
    }
}
