/**
 * 
 */
package com.intech.user.model;

/**
 * @author Akash Budhwani
 *
 */
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="users")
public class User {
	
	@Transient
	public static final String SEQUENCE = "users_sequence";
	
	@Id
	private long id;
	
	private String username;
	
	private String email;
	
	private String password;
	
}
