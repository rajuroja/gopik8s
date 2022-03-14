/**
 * 
 */
package com.intech.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Akash Budhwani
 *
 */

@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private long id;
	
	@NotNull(message="Please enter a user name")
	@NotEmpty(message = "Please enter a user name")
	private String username;
	
	@Email(message = "Please enter valid email")
	private String email;
	
	@Pattern(regexp = "^(?=.*\\d).{4,8}$", flags = Flag.UNICODE_CASE, message= "Please enter valid password")
	private String password;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
