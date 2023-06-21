package com.example.demo.orms;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "drone")
public class Drone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String serialNumber;
	private String model;
	private Integer weightLimit;
	private Integer batteryCapacity;
	private String state;

	@Column(name="id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="serial_number")
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name="model")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name="weight_limit")
	public Integer getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(Integer weightLimit) {
		this.weightLimit = weightLimit;
	}

	@Column(name="battery_capacity")
	public Integer getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(Integer batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	@Column(name="state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
