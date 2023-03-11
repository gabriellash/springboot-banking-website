package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankRepository extends CrudRepository<Bank, String> {
	//public List<Bank> findByName(String name);
}
