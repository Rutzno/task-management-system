package com.diarpy.taskmanagementsystem;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.2
 */

public interface MyUserRepository extends CrudRepository<MyUser, Integer> {
    MyUser findByEmail(String email);
    Optional<MyUser> findByEmailIgnoreCase(String email);

}
