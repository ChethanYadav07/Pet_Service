package com.pratian.petzey.pets.entites;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentHistory {
	private long appointmentId;
	private long parentId;
	private String vetImageURL;
	private String petImageURL;
	private String vetName;
	private String petName;
	private LocalTime time;
	private LocalDate date;
}
