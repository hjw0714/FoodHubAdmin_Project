package com.application.foodhubAdmin.repository;

import com.application.foodhubAdmin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMsRepository extends JpaRepository <User ,String> {

}
