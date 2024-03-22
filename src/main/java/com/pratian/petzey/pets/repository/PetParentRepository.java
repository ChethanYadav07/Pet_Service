package com.pratian.petzey.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratian.petzey.pets.entites.PetParent;

@Repository
public interface PetParentRepository extends JpaRepository<PetParent, Long>{

	@Query(value="select m.petParentId from PetParent m where m.email=:email")
	public long getIdbyEmail(@Param("email") String email);
}
