package com.example.healthapp.repository;

import com.example.healthapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsernameAndPasswordAndRole(String username, String password, String role);

    User findByUsernameAndPasswordAndRoleAndLicenseNumber(String username, String password, String role, String licenseNumber);
    User findByUsername(String username);


    Page<User> findAllByRole(String role, Pageable pageable);
}
