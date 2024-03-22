package com.pratian.petzey.pets.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pratian.petzey.pets.entites.AppointmentHistory;
import com.pratian.petzey.pets.entites.Pet;
import com.pratian.petzey.pets.entites.PetParent;
import com.pratian.petzey.pets.entites.Vet;
import com.pratian.petzey.pets.service.PetzeyService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/pet")
@CrossOrigin(origins = {"*"})
public class PetZeyController {

	@Autowired
	PetzeyService petService;
	final Logger logger = LoggerFactory.getLogger(PetZeyController.class);

	@PostMapping("/postPetParent")
	@Operation(summary = "To add new PetParent")
	public ResponseEntity<?> PostPetParent(@RequestBody @Valid PetParent parent) {
		logger.info("successfully added the Pet Parent information ");
		return new ResponseEntity<PetParent>(petService.saveparent(parent), HttpStatus.CREATED);
	}

	
	@PostMapping("/postpet/{parentId}")
	@Operation(summary = "To Save the Pet by PetParentId")
	public ResponseEntity<?> PostPet(@PathVariable("parentId") long parentId, @RequestBody @Valid Pet pet) {
		logger.info("successfully added the Pet information ");
		return new ResponseEntity<Pet>(petService.savePet(parentId, pet), HttpStatus.CREATED);
	}

	@PutMapping("/Petparent")
	@Operation(summary = "To edit the Details Pet parent")
	public ResponseEntity<?> PutParent(@RequestBody PetParent petParent) {
		logger.info("successfully edited the Pet Parent information ");
		return new ResponseEntity<PetParent>(petService.editPetParent(petParent), HttpStatus.OK);
	}

	@PutMapping("/Pet")
	@Operation(summary = "To edit The Details pet")
	public ResponseEntity<?> PutPet(@RequestBody Pet pet) {
		logger.info("successfully edited the Pet information ");
		return new ResponseEntity<Pet>(petService.editPet(pet), HttpStatus.OK);
	}

	@GetMapping("/get/{id}")
	@Operation(summary = "to get the pet with given id")
	public ResponseEntity<?> getPetById(@PathVariable("id") int id) {
		logger.info("successfully retrieved the Pet information for the id " + id);
		return new ResponseEntity<>(petService.getPetById(id), HttpStatus.OK);

	}

	@GetMapping("/getAll/{offset}/{pageSize}")
	@Operation(summary = "To get all the pets")
	public ResponseEntity<?> getAllpet(@PathVariable int offset, @PathVariable int pageSize) {
		logger.info("successfully retrieved all the Pets information present in the database");
		return new ResponseEntity<>(petService.getAllPets(offset, pageSize), HttpStatus.OK);

	}

	@GetMapping("/getParentByID/{petParentId}")
	@Operation(summary = "To get Pet parent by id")
	public ResponseEntity<PetParent> getParentById(@PathVariable("petParentId") long petParentId) {
		logger.info("successfully edited the Pet Parent information ");
		return new ResponseEntity<PetParent>(petService.getPetParent(petParentId), HttpStatus.OK);
	}

	@GetMapping("/getpetByParentId/{petParentId}")
	@Operation(summary = "To get Pet By ParentId")
	public ResponseEntity<List<Pet>> getPetsByParentId(@PathVariable long petParentId) {
		List<Pet> pets = petService.getPetsByParentId(petParentId);
		return new ResponseEntity<List<Pet>>(petService.getPetsByParentId(petParentId), HttpStatus.OK);

	}

//	@GetMapping("/getAppointmentByParentId/{parentId}")
//	@Operation(summary = "To get appointments by parentID")
//	public ResponseEntity<List<AppointmentHistory>> getAppointmentHistory(@PathVariable("parentId") long parentId) {
//		return new ResponseEntity<List<AppointmentHistory>>(petService.getAppointmentHistoryByParentId(parentId),
//				HttpStatus.OK);
//
//	}

//	@GetMapping("/getAllVets")
//	@Operation(summary = "To get all vets")
//	public ResponseEntity<List<Vet>> getAllVets() {
//		logger.info("successfully retrieved all the vets information present in the database");
//
//		return new ResponseEntity<List<Vet>>(petService.getAllVets(), HttpStatus.OK);
//
//	}
//
	@GetMapping("/getIdByEmail/{email}")
	@Operation(summary="to get the id based on email")
	public ResponseEntity<Long>getIdByEmail(@PathVariable("email")String email)
	{
		logger.info("successfully retrieved the id");
		return new ResponseEntity<Long>(petService.getIdbyEmail(email),HttpStatus.OK);
	}

	
	@GetMapping("/getallparents")
	@Operation(summary="to get all the petparent")
	public ResponseEntity<List<PetParent>> getAllparents(){
		return new ResponseEntity<List<PetParent>>(petService.getAllparents(),HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	@Operation(summary="to get all the pets")
	public ResponseEntity<?> getAllpets() {
		ResponseEntity<?> response = null;
		
			response = new ResponseEntity<List<Pet>>(petService.getAllPets(), HttpStatus.OK);
		
		return response;
	}
}