/**
 * 
 */
package com.intech.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akash Budhwani
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
	
	private long id;
	
	private String movieName;
	
	private String overview;
	
	private String relaseYear;
	
	private float movieRatings;

}
