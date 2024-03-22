package com.pratian.petzey.pets.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratian.petzey.pets.entites.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>{
	
	@Query("SELECT p FROM Pet p WHERE p.petParent.petParentId = :parentId")
    List<Pet> findPetsByParentId(@Param("parentId") long parentId);
   

}