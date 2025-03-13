package com.shayan.journalyt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.shayan.journalyt.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

}
