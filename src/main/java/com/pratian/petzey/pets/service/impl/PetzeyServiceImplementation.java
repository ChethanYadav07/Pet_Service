
package com.pratian.petzey.pets.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pratian.petzey.pets.controller.PetZeyController;
import com.pratian.petzey.pets.entites.AppointmentHistory;
import com.pratian.petzey.pets.entites.Pet;
import com.pratian.petzey.pets.entites.PetParent;
import com.pratian.petzey.pets.entites.Vet;
import com.pratian.petzey.pets.exceptions.ParentNotFoundException;
import com.pratian.petzey.pets.exceptions.PetNotFoundException;
import com.pratian.petzey.pets.repository.PetParentRepository;
import com.pratian.petzey.pets.repository.PetRepository;
import com.pratian.petzey.pets.service.PetzeyService;

@Service
public class PetzeyServiceImplementation implements PetzeyService {

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetParentRepository petParentRepository;

	@Autowired
	RestTemplate restTemplate;
	
	final Logger logger = LoggerFactory.getLogger(PetZeyController.class);

	@Override
	public PetParent saveparent(PetParent parent) {

		return petParentRepository.save(parent);
	}

	@Override
	public Pet savePet(long parentId, Pet pet) {
		Optional<PetParent> optionalParent = petParentRepository.findById(parentId);
		if (optionalParent.isPresent()) {
			PetParent parent = optionalParent.get();
			pet.setPetParent(parent);
			parent.getPets().add(pet);
			petParentRepository.save(parent);
		} 
		return pet;
	}

	@Override
	public PetParent editPetParent(PetParent petParent) {
		if (petParentRepository.existsById(petParent.getPetParentId())) {
			return petParentRepository.save(petParent);
		} else {
			throw new ParentNotFoundException("No PetParent found for the given Id ");
		}
	}


	@Override
	public Pet editPet(Pet pet) {
		if (petRepository.existsById(pet.getPetId())) {
			return petRepository.save(pet);
		}
		else {
			throw new PetNotFoundException("No Pet found for the given Id");
		}
	}

	@Override
	public Pet getPetById(long id) {
		return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException("No Pet found for the given Id "));
	}

	@Override
		public Page<Pet> getAllPets(int offset,int pageSize){
	    	Page<Pet> pets=petRepository.findAll(PageRequest.of(offset, pageSize));
	    	if(pets.isEmpty()) {
	    		throw new PetNotFoundException("No Pet found");
	    	}
	    	return pets;
	    }

	@Override
	public PetParent getPetParent(long petParentId) {
		PetParent petParent=petParentRepository.findById(petParentId).orElseThrow(() -> new ParentNotFoundException("No Pet found for the given Id "));
		return petParent;
	}

	@Override
	public List<Pet> getPetsByParentId(long parentId) {
		return petRepository.findPetsByParentId(parentId);
	}
//	public List<AppointmentHistory>getAppointmentHistoryByParentId(long parentId)
//	{
//		List<AppointmentHistory>filteredAppointments=new ArrayList<AppointmentHistory>();
//		AppointmentHistory[] allAppointments = restTemplate.getForObject("https://apigateway.bt.skillassure.com/appointment/appointment/getall", AppointmentHistory[].class);
//		// Filter appointments based on the parent ID
//		for (AppointmentHistory appointment : allAppointments) {
//			if (appointment.getParentId()==parentId) {
//				filteredAppointments.add(appointment);
//			}
//		}
//		return filteredAppointments;
//	}
//
//	public List<Vet>getAllVets()
//
//	{
//		List<Vet>allVets=new ArrayList<Vet>();
//		System.out.println("entered the service layer");
//		logger.info("entered the service layer");
//		long starttime = System.currentTimeMillis();
//		List<Vet> vets = (List<Vet>) restTemplate.getForObject("https://apigateway.bt.skillassure.com/Vet/api/vet/get", Vet.class);
//		long endTime = System.currentTimeMillis();
//		logger.info("Time taken to execute GetVets {}",endTime- starttime);
//		
//		return allVets;
//	}

	@Override
	public long getIdbyEmail(String email) {
		return petParentRepository.getIdbyEmail(email);
	}

	@Override
	public List<PetParent> getAllparents() {
		return petParentRepository.findAll();
	}

	@Override
	public List<Pet> getAllPets() {
		List<Pet> pets = petRepository.findAll();
		return pets;
	}
}
