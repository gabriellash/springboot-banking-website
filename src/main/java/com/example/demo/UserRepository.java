package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("Select u from User u Where u.email =?1")
	User findByEmail(String email);
	
	
	@Query("Select u From User u Where u.email =?1 and u.password=?2")
	User findByEP(String email, String password);
}
