package com.pratian.petzey.pets.service;

import java.util.List;

import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Page;

import com.pratian.petzey.pets.entites.AppointmentHistory;
import com.pratian.petzey.pets.entites.Pet;
import com.pratian.petzey.pets.entites.PetParent;
import com.pratian.petzey.pets.entites.Vet;

public interface PetzeyService {
	PetParent saveparent(PetParent parent);

	Pet savePet(long parentId, Pet pet);

	PetParent editPetParent(PetParent petParent);

	Pet editPet(Pet pet);

	Pet getPetById(long id);

	public Page<Pet> getAllPets(int offset, int pageSize);

	public PetParent getPetParent(long petParentId);

	public List<Pet> getPetsByParentId(long parentId);

//	public List<AppointmentHistory> getAppointmentHistoryByParentId(long parentId);
//
//	public List<Vet> getAllVets();

	public long getIdbyEmail(String email);

	public List<PetParent>getAllparents();
	
	public List<Pet> getAllPets();
}
