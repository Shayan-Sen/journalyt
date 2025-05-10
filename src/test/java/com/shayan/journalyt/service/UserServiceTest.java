package com.shayan.journalyt.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shayan.journalyt.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUserName(){
        assertNotNull(userRepository.findByUsername("shayan-boot"));
    }
}
