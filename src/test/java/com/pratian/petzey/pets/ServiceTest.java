package com.pratian.petzey.pets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.pratian.petzey.pets.entites.Pet;
import com.pratian.petzey.pets.entites.PetParent;
import com.pratian.petzey.pets.enums.ParentGender;
import com.pratian.petzey.pets.enums.PetGender;
import com.pratian.petzey.pets.enums.Species;
import com.pratian.petzey.pets.exceptions.ParentNotFoundException;

import com.pratian.petzey.pets.exceptions.PetNotFoundException;

import com.pratian.petzey.pets.repository.PetParentRepository;
import com.pratian.petzey.pets.repository.PetRepository;
import com.pratian.petzey.pets.service.PetzeyService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)

public class ServiceTest {

	@MockBean
	PetRepository petRepository;

	@MockBean
	PetParentRepository parentRepository;

	@Autowired
	PetzeyService petzeyService;

	Pet pet;
	PetParent petParent;
	List<Pet> pets;
	List<PetParent>petParents;

	AutoCloseable autoCloseable;

	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		pets = new ArrayList<>();
		Date date = new Date(2021 - 01 - 12);
		pet = new Pet(1l," ", "Charile",  Species.Cat, PetGender.Male, 12, "B+", date, "itching", "cough", null);
		pets.add(pet);
		petParent = new PetParent(101l,null, "Naveen", "6363460544", "Mumbai",  ParentGender.Mr, null, pets);
		pet.setPetParent(petParent);
		petParents=new ArrayList<PetParent>();
		petParents.add(petParent);
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

	@Test
	public void testSavePet() {
		Optional<PetParent> optionalParent = Optional.of(petParent);
		Mockito.when(parentRepository.findById(petParent.getPetParentId())).thenReturn(optionalParent);
		Pet pet1 = petzeyService.savePet(petParent.getPetParentId(), pet);
		assertEquals("Charile", pet1.getPetName());
	}

	@Test
	public void testSavePetParent() {

		Mockito.when(parentRepository.save(petParent)).thenReturn(petParent);
		PetParent servicePet = petzeyService.saveparent(petParent);
		assertEquals("Naveen", petParent.getPetParentName());
	}

	@Test
	public void testEditPetParent() {
		petParent.setPetParentName("poornesh");
		Mockito.when(parentRepository.existsById(petParent.getPetParentId())).thenReturn(true);
		Mockito.when(parentRepository.save(petParent)).thenReturn(petParent);
		PetParent editParentService = petzeyService.editPetParent(petParent);
		assertNotNull(editParentService);
		assertEquals("poornesh", editParentService.getPetParentName());
	}

	@Test
	public void testEditPetParentException() {
		Mockito.when(parentRepository.existsById(petParent.getPetParentId())).thenReturn(false);
		assertThrows(ParentNotFoundException.class, () -> petzeyService.editPetParent(petParent));
	}

	@Test
	public void testEditPet() {
		pet.setPetName("poornesh");
		Mockito.when(petRepository.existsById(pet.getPetId())).thenReturn(true);
		Mockito.when(petRepository.save(pet)).thenReturn(pet);
		Pet editParentService = petzeyService.editPet(pet);
		assertNotNull(editParentService);
		assertEquals("poornesh", editParentService.getPetName());
	}

	@Test
	public void testEditPetException() {
		Mockito.when(petRepository.existsById(pet.getPetId())).thenReturn(false);
		assertThrows(PetNotFoundException.class, () -> petzeyService.editPet(pet));
	}

	@Test
	public void testGetPetById() {
		Optional<Pet> optionalPet = Optional.of(pet);
		Mockito.when(petRepository.findById(1l)).thenReturn(optionalPet);
		Pet testPet = petzeyService.getPetById(1l);
		assertEquals("Charile", testPet.getPetName());
	}

	@Test
	public void testGetPetByIdException() {
		Optional<Pet> optionalPet = Optional.of(pet);
		Mockito.when(petRepository.findById(1l)).thenReturn(optionalPet);
		assertThrows(PetNotFoundException.class, () -> petzeyService.getPetById(2l));
	}

	@Test
	public void testGetAllPets() {
		Mockito.when(petRepository.findAll()).thenReturn(pets);
		List<Pet> testPets = petzeyService.getAllPets();
		assertEquals(1, testPets.size());
	}
	@Test
	public void testGetAllPetsWithPagination() {
	      when(petRepository.findAll(any(PageRequest.class)))
          .thenReturn(new PageImpl<>(pets));

      // Call the getAllPets method
      Page<Pet> result = petzeyService.getAllPets(0, 2);

      // Verify that the correct page of pets is returned
      assertEquals(1, result.getContent().size());
      assertEquals("Charile", result.getContent().get(0).getPetName());
	}

	@Test
	public void testGetAllPetsWithPaginationException() {
	      when(petRepository.findAll(any(PageRequest.class)))
          .thenReturn(new PageImpl<>(new ArrayList()));
      assertThrows(PetNotFoundException.class, () -> petzeyService.getAllPets(0, 2));
	}
	
	@Test
	public void testGetParentById()
	{
		Optional<PetParent> optionalParent = Optional.of(petParent);
		Mockito.when(parentRepository.findById(1l)).thenReturn(optionalParent);
		PetParent petParent= petzeyService.getPetParent(1);
		assertEquals("Naveen",petParent.getPetParentName() );
	}

	@Test
	public void testGetParentByIdException() {
		Optional<PetParent> optionalPet = Optional.of(petParent);
		Mockito.when(parentRepository.findById(1l)).thenReturn(optionalPet);
		assertThrows(PetNotFoundException.class, () -> petzeyService.getPetById(2l));
	}
	@Test
	public void testGetPetsByParentId()
	{
		Mockito.when(petRepository.findPetsByParentId(1)).thenReturn(pets);
		petzeyService.getPetsByParentId(1);
		assertEquals(1, pets.size());
	}

	@Test
	public void testGetAllParents()
	{
		Mockito.when(parentRepository.findAll()).thenReturn(petParents);
		List<PetParent>petParents=petzeyService.getAllparents();
		assertEquals(1l, petParents.size());
	}
	
	@Test
	public void testGetIdByEmailId()
	{
	Mockito.when(parentRepository.getIdbyEmail("naveenka2347@gmail.com")).thenReturn(1l);	
	long id=petzeyService.getIdbyEmail("naveenka2347@gmail.com");
	assertEquals(1l, id);
	}


}
