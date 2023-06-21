package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.orms.Drone;
import com.example.demo.orms.Medication;
import com.example.demo.services.DemoService;

@RestController
public class DemoController {

	@Autowired
	DemoService demoService;

	// registering a drone
	@PostMapping("/register-drone")
	public ResponseEntity<Drone> addDrone(@RequestBody Drone drone) {
		Drone savedDrone = demoService.addDrone(drone);
		return ResponseEntity.ok().body(savedDrone);
	}

	// loading a drone with medication items
	@PostMapping("/load-medication")
	public ResponseEntity<Medication> add(@RequestBody Medication med) {
		Medication savedMed = demoService.addMedication(med);
		return ResponseEntity.ok().body(savedMed);
	}

	// checking loaded medication items for a given drone
	@GetMapping("/medication/{id}")
	public ResponseEntity<List<Medication>> getMedicationsByDroneId(@PathVariable("id") int id) {
		List<Medication> meds = demoService.getMedsByDroneId(id);
		return ResponseEntity.ok().body(meds);
	}

	// checking available drones for loading
	@GetMapping("/available-drones")
	public ResponseEntity<List<Drone>> getAvailableDrones() {
		List<Drone> availableDrones = demoService.getAvailableDrones();
		return ResponseEntity.ok().body(availableDrones);
	}

	// check drone battery level for a given drone
	@GetMapping("/battery-level/{id}")
	public ResponseEntity<Integer> getDroneBatteryLevel(@PathVariable("id") int id) {
		Integer batteryLevel = demoService.getDroneBatteryCapacityById(id);
		return ResponseEntity.ok().body(batteryLevel);
	}

	// retrieving all drones
	@GetMapping("/drones")
	public List<Drone> getAllDrones() {
		return demoService.getAllDrones();
	}

	// retrieving all medications
	@GetMapping("/medications")
	public List<Medication> getAllMeds() {
		return demoService.getAllMeds();
	}

	// adding an image to medication
	@PostMapping("/addImage/{id}")
	public  ResponseEntity<Medication>  addImage(@RequestParam("file") MultipartFile file, int id) throws IOException {
		Medication med = demoService.addImageToMedication(file, id);
		return ResponseEntity.ok().body(med);
	}

}
