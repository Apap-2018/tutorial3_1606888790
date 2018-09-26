package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tutorial3.model.PilotModel;

@Service
public class PilotInMemoryService implements PilotService {
	private List<PilotModel> archivePilot;
	
	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		archivePilot.add(pilot);
		
	}

	@Override
	public List<PilotModel> getPilotList() {
		return archivePilot;
	}

	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		// TODO membuat searching algo yg lebih bagus
		
		if(!archivePilot.isEmpty()) {
			for(int i = 0; i < archivePilot.size(); i++) {
				if(archivePilot.get(i).getLicenseNumber().equalsIgnoreCase(licenseNumber))
					return archivePilot.get(i);
		
			}
		}
		
		
		return null;
	}

	@Override
	public PilotModel updatePilotFH(String licenseNumber, Integer flyHour) {
		PilotModel pilotUpdated = getPilotDetailByLicenseNumber(licenseNumber);
		if(pilotUpdated!=null)
			pilotUpdated.setFlyHour(flyHour);
		return pilotUpdated;
	}

	@Override
	public boolean deletePilot(String id) {
		if(!archivePilot.isEmpty()) {
			for(int i = 0; i < archivePilot.size(); i++) {
				if(archivePilot.get(i).getId().equalsIgnoreCase(id)) {
					archivePilot.remove(i); 
					return true;
				}
					
		
			}
		}
		
		
		return false;
	}
	
}
