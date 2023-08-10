package com.topdf.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.topdf.project.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
}