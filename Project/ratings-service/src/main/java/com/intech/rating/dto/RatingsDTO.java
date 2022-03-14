/**
 * 
 */
package com.intech.rating.dto;

import java.util.Date;

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
public class RatingsDTO {
	
	private long id;
	
	private long movieId;
	
	private long userId;
	
	private float ratings;

	private Date date;
}
