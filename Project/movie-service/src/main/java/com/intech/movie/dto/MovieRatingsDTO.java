/**
 * 
 */
package com.intech.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akash Budhwani
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieRatingsDTO {
	
	private long id;
	
	private float ratings;

}
