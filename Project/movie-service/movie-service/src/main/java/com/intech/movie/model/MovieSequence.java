/**
 * 
 */
package com.intech.movie.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection ="movie_sequence")
public class MovieSequence {
	
	@Id
	private String id;
	
	private long seq;

}
