package com.intech.user.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRatResDTO {
	
	private long movieid;
	
	private String movieName;
	
	private String overview;
	
	private String relaseYear;
	
	private float movieRatings;
	
	private float userRatings;

	private Date ratingsDate;

}
