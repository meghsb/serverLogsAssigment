package com.cs.example.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cs.example.model.Alert;

@Repository
public interface AlertRepository extends CrudRepository<Alert, String> {
	
}