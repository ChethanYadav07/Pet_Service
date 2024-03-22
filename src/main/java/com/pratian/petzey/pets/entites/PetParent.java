package com.pratian.petzey.pets.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pratian.petzey.pets.enums.ParentGender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PetParent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long petParentId;
	private String imageURL;
	@NotNull(message="ParentName should not be null")
	@Size(min = 3, max=16)
	private String petParentName;
	@Size(max = 10,min = 10,message ="PhoneNumber should have 10 digits" )
	@Pattern(regexp = "^[0-9]*$",message="phone number should have only numbers")
	private String phoneNumber;

	private String address;
	@Enumerated(EnumType.STRING)
	private ParentGender gender;
	private String email;
	@JsonIgnore
	@OneToMany(mappedBy = "petParent", cascade = CascadeType.ALL)
	private List<Pet> pets = new ArrayList<Pet>();

}