/**
 * 
 */
package com.intech.rating.model;

import java.util.Date;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ratings")
public class Ratings {
		
		@Transient
		public static final String SEQUENCE="ratings_sequence";

		@Id
		private long id;
		
		private long movieId;
		
		private long userId;
		
		private float ratings;
		
		private Date date;
		
}
