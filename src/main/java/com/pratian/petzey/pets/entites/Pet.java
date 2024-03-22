package com.pratian.petzey.pets.entites;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pratian.petzey.pets.enums.PetGender;
import com.pratian.petzey.pets.enums.Species;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long petId;
	private String imageURL;
	@NotNull(message="PetName should not be null")
	@Size(min = 3, max=16)
	private String petName;
	@Enumerated(EnumType.STRING)
	private Species breed;
	@Enumerated(EnumType.STRING)
	private PetGender gender;
	private Integer age;
	private String bloodGroup;
	private Date dateOfBirth;
	private String allergis;
	private String symptom;


	@ManyToOne
	@JoinColumn(name = "petParentId")
	private PetParent petParent;

}