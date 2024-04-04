package com.telran.bankapplication.repository;

import com.telran.bankapplication.entity.User;
import com.telran.bankapplication.entity.enums.UserStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.role = 'CLIENT' and u.status = :status")
    List<User> getAllClientsByStatus(@Param("status") UserStatus status);
}
