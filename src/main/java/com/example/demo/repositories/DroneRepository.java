package com.example.demo.repositories;

import com.example.demo.orms.Drone;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;



public interface DroneRepository extends JpaRepository<Drone, Integer>  {

	Optional<Drone> findById(Integer id);
	List<Drone> findByState(String state);
}
