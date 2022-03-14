/**
 * 
 */
package com.intech.user.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.intech.user.dto.RatingsDTO;


/**
 * @author Akash Budhwani
 *
 */
@FeignClient(name="ratings-service")
public interface RatingsServiceProxy {
	
	@GetMapping(value = "/api/ratings/userRatings/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RatingsDTO> getUserRatings(@PathVariable("userId") long userId);

}