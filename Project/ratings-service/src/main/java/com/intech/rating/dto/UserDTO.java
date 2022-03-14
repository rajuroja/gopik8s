/**
 * 
 */
package com.intech.rating.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

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
public class UserDTO {
	
	private long id;
	
	@NotNull(message="Please enter a user name")
	@NotEmpty(message = "Please enter a user name")
	private String username;
	
	@Email(message = "Please enter valid email")
	private String email;
	
	@Pattern(regexp = "^(?=.*\\d).{4,8}$", flags = Flag.UNICODE_CASE, message= "Please enter valid password")
	private String password;

	
	

}
