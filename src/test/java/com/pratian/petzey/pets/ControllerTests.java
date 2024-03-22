package com.pratian.petzey.pets;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratian.petzey.pets.entites.Pet;
import com.pratian.petzey.pets.entites.PetParent;
import com.pratian.petzey.pets.enums.ParentGender;
import com.pratian.petzey.pets.enums.PetGender;
import com.pratian.petzey.pets.enums.Species;
import com.pratian.petzey.pets.exceptions.ParentNotFoundException;
import com.pratian.petzey.pets.exceptions.PetNotFoundException;
import com.pratian.petzey.pets.service.PetzeyService;

@WebMvcTest
class ControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetzeyService petservice;

	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void getPetByIdTest() throws Exception {
		Pet p = new Pet();
		p.setPetId(1L);
		when(petservice.getPetById(1L)).thenReturn(p);
		mockMvc.perform(get("/pet/get/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.petId", Matchers.equalTo(1)));
	}

	@Test
	public void postPetByParentIdTest() throws Exception {
		PetParent parent = new PetParent(1l,"","Ram","9087654321","NewYork",ParentGender.Mr,"ram123@gmail.com",null);
		String json = mapper.writeValueAsString(parent);
		when(petservice.saveparent(ArgumentMatchers.any())).thenReturn(parent);
		mockMvc.perform(post("/pet/postPetParent").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isCreated());
		
		Pet pet = new Pet();
		pet.setPetId(2l);
		pet.setBreed(Species.Dog);
		pet.setGender(PetGender.Male);
		pet.setPetParent(parent);
		pet.getPetParent().setPetParentId(1l);
		pet.getPetParent().setGender(ParentGender.Mr);
		when(petservice.savePet(parent.getPetParentId(), pet)).thenReturn(pet);
		mockMvc.perform(post("/pet/postpet/1l").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isBadRequest());
		        
  
	}

	@Test
	public void getPetByIdExceptionTest() throws Exception{
		when(petservice.getPetById(1L)).thenThrow(new PetNotFoundException());
		mockMvc.perform(get("/pet/get/1"))
		.andExpect(status().isNotFound());
	}

//	@Test
//	public void testGetAllPets() throws Exception {
//		List<Pet> pets = new ArrayList<>();
//		Pet pet = new Pet();
//		pet.setPetName("Tom");
//		pet.setBloodGroup("b+ve");
//		pet.setAge(12);
//		pet.setAllergis("skin");
//		pets.add(pet);
//		when(petservice.getAllPets()).thenReturn(pets);
//		mockMvc.perform(get("/pet/getAll")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
//				.andExpect(jsonPath("$[0].petName", Matchers.equalTo("Tom")));
//	}
//
//	@Test
//	public void exceptiongetAllPets() throws Exception {
//		when(petservice.getAllPets()).thenThrow(new PetNotFoundException());
//		mockMvc.perform(get("/pet/getAll"))
//		.andExpect(status().isNotFound());
//	}
//
	@Test
	public void postParentTest() throws Exception {
		PetParent parent = new PetParent(1l,"","Ram","9087654321","NewYork",ParentGender.Mr,"ram123@gmail.com",null);
		String json = mapper.writeValueAsString(parent);
		when(petservice.saveparent(ArgumentMatchers.any())).thenReturn(parent);
		mockMvc.perform(post("/pet/postPetParent").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).characterEncoding("utf-8")).andExpect(status().isCreated())
				.andExpect(jsonPath("$.petParentId", Matchers.equalTo(1)))
				.andExpect(jsonPath("$.address", Matchers.equalTo("NewYork")))
				.andExpect(jsonPath("$.phoneNumber", Matchers.equalTo("9087654321")));	
	    

	}

	
	@Test
	public void postParentExceptionTest() throws Exception {
		PetParent parent = new PetParent();
		mockMvc.perform(post("/postPetParent").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void editPetParentTestException() throws Exception {
		PetParent parent = new PetParent();
		parent.setPetParentName("xyz");
		parent.setPetParentName("abc");
		when(petservice.editPetParent(parent)).thenThrow(new ParentNotFoundException());
		mockMvc.perform(put("/pet/Petparent").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(parent))).andExpect(status().isNotFound());
	}

	@Test
	public void editPetParentTest() throws Exception {
		PetParent parent = new PetParent();
		parent.setPetParentName("xyz");
		parent.setPetParentName("abc");
		when(petservice.editPetParent(parent)).thenReturn(parent);
		mockMvc.perform(put("/pet/Petparent").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(parent))).andExpect(status().isOk());
	}

	@Test
	public void editPetTestException() throws Exception {
		Pet pet = new Pet();
		pet.setAge(2);
		pet.setAge(3);
		when(petservice.editPet(pet)).thenThrow(new PetNotFoundException());
		mockMvc.perform(put("/pet/Pet").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(pet)))
				.andExpect(status().isNotFound());
	}

	@Test
	public void editPetTest() throws Exception {
		Pet pet = new Pet();
		pet.setAge(2);
		pet.setAge(3);
		when(petservice.editPet(pet)).thenReturn(pet);
		mockMvc.perform(put("/pet/Pet").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(pet)))
				.andExpect(status().isOk());
	}

	@Test
	public void getParentbyId() throws Exception {
		PetParent parent = new PetParent();
		parent.setPetParentId(1l);
		when(petservice.getPetParent(1l)).thenReturn(parent);
		mockMvc.perform(get("/pet/getParentByID/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.petParentId", Matchers.equalTo(1)));
	}

	@Test
	public void getParentByIdExceptionTest() throws Exception {
//		when(petservice.getPetById(1L)).thenThrow(new ParentNotFoundException());
//		mockMvc.perform(get("/pet/getParentByID/1"))
//		.andExpect(status().isNotFound());

		long petParentId = 1;
		when(petservice.getPetParent(petParentId)).thenReturn(null);
		mockMvc.perform(get("/getParentByID/{petParentId}", petParentId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

//	@Test
//	public void getPetByParentId() throws Exception {
//		 long petParentId = 1;
//		 PetParent parent=new PetParent();
//		 parent.setPetParentId(petParentId);
//		 parent.setPetParentId(1l);
//	        Pet pet1 = new Pet();
//	        pet1.setPetId(1l);
//	        pet1.setPetName("David");
//	        pet1.setPetParent(parent);
//	        Pet pet2 = new Pet();
//	        pet2.setPetId(2l);
//	        pet2.setPetName("Nithin");
//	        pet2.setPetParent(parent);
//
//	        List<Pet> expectedPets = Arrays.asList(pet1, pet2);
//	        when( petservice.getPetsByParentId(petParentId)).thenReturn(expectedPets);
//	        mockMvc.perform(get("/pets/getpetByParentId/1")
//	                .accept(MediaType.APPLICATION_JSON))
//	                .andExpect(status().isOk())
//	                .andExpect(jsonPath("$", Matchers.hasSize(2)));
//	                
//	  
//	    
//	}
}
