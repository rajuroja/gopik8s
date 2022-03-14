/**
 * 
 */
package com.intech.movie.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection =  "movie")
public class Movie {
	
	@Transient
	public static final String  SEQUENCE = "movie_sequence";
	
	@Id
	private long id;
	
	private String movieName;
	
	private String overview;
	
	private String relaseYear;
	
	@Transient
	private float movieRatings;
		
}
