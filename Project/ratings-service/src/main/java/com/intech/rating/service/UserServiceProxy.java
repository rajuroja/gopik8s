/**
 * 
 */
package com.intech.rating.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.intech.rating.dto.UserDTO;

/**
 * @author Akash Budhwani
 *
 */
@FeignClient(name="user-service")
public interface UserServiceProxy {
	
	@GetMapping(value = "/api/users/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO getUserDetails(@PathVariable("userId") long userId);

}
