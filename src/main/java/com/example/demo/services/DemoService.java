package com.example.demo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.enums.DroneModel;
import com.example.demo.enums.DroneState;
import com.example.demo.orms.Drone;
import com.example.demo.orms.Medication;
import com.example.demo.repositories.DroneRepository;
import com.example.demo.repositories.MedicationRepository;

@Service
public class DemoService {

	@Autowired
	DroneRepository droneRepository;

	@Autowired
	MedicationRepository medicationRepository;

	// ------------- Retrieve

	public List<Drone> getAllDrones() {
		List<Drone> drones = new ArrayList<Drone>();
		droneRepository.findAll().forEach(drone -> drones.add(drone));
		return drones;
	}

	public List<Drone> getAvailableDrones() {
		return droneRepository.findByState(DroneState.IDLE.toString());
	}

	public Integer getDroneBatteryCapacityById(Integer id) {
		Optional<Drone> drone = droneRepository.findById(id);
		if (drone.isPresent())
			return drone.map(Drone::getBatteryCapacity).orElse(0);
		return 0;
	}

	public List<Medication> getAllMeds() {
		List<Medication> meds = new ArrayList<Medication>();
		medicationRepository.findAll().forEach(med -> meds.add(med));
		return meds;
	}

	public Medication getMedById(Integer id) {
		Optional<Medication> med = medicationRepository.findById(id);
		if (med.isPresent())
			return med.orElse(null);
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Medication> getMedsByDroneId(Integer id) {
		Optional<Medication> meds = medicationRepository.findByDroneId(id);
		if (meds.isPresent())
			return (List<Medication>) meds.map(med -> med).orElse(null);
		return null;
	}

	// ------------- Add/Update
	public Drone addDrone(Drone drone) {
		try {
			validateAddDrone(drone);
			drone.setBatteryCapacity(100);
			drone.setState(DroneState.IDLE.toString());
			return droneRepository.save(drone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return drone;
	}

	public Medication addMedication(Medication medication) {
		try {

			validateAddMedication(medication);
			List<Drone> avail = getAvailableDrones();
			if (avail.isEmpty())
				throw new Exception("No available drones");

			Drone selectedDrone = new Drone();
			boolean availability = false;
			for (Drone d : avail) {
				validateLoadDrone(d, medication);
				if (d.getBatteryCapacity() > 25) {
					selectedDrone = d;
					availability = true;
					break;
				}
			}

			if (!availability)
				throw new Exception("No available drones");

			selectedDrone.setState(DroneState.LOADED.toString());
			droneRepository.save(selectedDrone);

			medication.setDroneId(selectedDrone.getId());

			return medicationRepository.save(medication);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medication;
	}

	public Medication addImageToMedication(MultipartFile file, int id) throws IOException {
		Medication med = getMedById(id);
		med.setImage(file.getBytes());
		medicationRepository.save(med);
		return med;
	}

	// ------------- Validations

	public void validateAddDrone(Drone drone) throws Exception {
		if (drone.getSerialNumber() == null)
			throw new Exception("Serial number is mandatory");
		if (drone.getModel() == null)
			throw new Exception("Model is mandatory");
		if (drone.getWeightLimit() == null)
			throw new Exception("Weight loimit is mandatory");

		if (drone.getSerialNumber().length() > 100)
			throw new Exception("serial number should be 100 characters max");

		List<String> models = Stream.of(DroneModel.values()).map(DroneModel::name).collect(Collectors.toList());
		if (!models.contains(drone.getModel()))
			throw new Exception("model should be (Lightweight, Middleweight, Cruiserweight, Heavyweight)");

		if (drone.getWeightLimit() > 500)
			throw new Exception("Weight loimit 500gr max");

		List<String> states = Stream.of(DroneState.values()).map(DroneState::name).collect(Collectors.toList());
		if (!states.contains(drone.getState()))
			throw new Exception("state should be (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING)");
	}

	public void validateAddMedication(Medication med) throws Exception {
		if (med.getName() == null)
			throw new Exception("Name is mandatory");
		if (med.getWeight() == null)
			throw new Exception("Weight is mandatory");
		if (med.getCode() == null)
			throw new Exception("Code is mandatory");

		if (!med.getName().matches("^[a-zA-Z0-9_-]*$"))
			throw new Exception("name is allowed only letters, numbers, ‘-‘, ‘_’");

		if (!med.getName().matches("^[A-Z0-9_]*$"))
			throw new Exception("code IS allowed only upper case letters, underscore and numbers");
	}

	public void validateLoadDrone(Drone drone, Medication med) throws Exception {
		if (drone.getWeightLimit() < med.getWeight())
			throw new Exception("drone can't be loaded with more weight that it can carry");

	}

}
