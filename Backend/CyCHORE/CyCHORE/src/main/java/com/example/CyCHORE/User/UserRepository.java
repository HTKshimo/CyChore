package com.example.CyCHORE.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserById(int id);
    User getGroupUserById(int group_id);
}

