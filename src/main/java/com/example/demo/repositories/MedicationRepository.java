package com.example.demo.repositories;

import com.example.demo.orms.Drone;
import com.example.demo.orms.Medication;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MedicationRepository extends JpaRepository<Medication, Integer>  {
	
	Optional<Medication> findByDroneId(Integer id);
}
