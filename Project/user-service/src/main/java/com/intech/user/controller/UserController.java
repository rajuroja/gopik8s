/**
 * 
 */
package com.intech.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intech.user.dto.MovieDTO;
import com.intech.user.dto.RatingsDTO;
import com.intech.user.dto.UserDTO;
import com.intech.user.dto.UserRatResDTO;
import com.intech.user.model.User;
import com.intech.user.service.MovieServiceProxy;
import com.intech.user.service.RatingsServiceProxy;
import com.intech.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Akash Budhwani
 *
 */
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RatingsServiceProxy ratingsProxy;

	@Autowired
	private MovieServiceProxy movieProxy;


	@PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) {
		log.info("Inside UserController :: createUser()");
		try {
			if (userService.findUserbyEmail(userDTO.getEmail()).isPresent()) {
				return new ResponseEntity<>("User already exists", HttpStatus.OK);
			} else {
				User user = userService.saveUser(userDTO);
				UserDTO userDTODetails = userService.convertEntityToDTO(user);
				return new ResponseEntity<>(userDTODetails, HttpStatus.CREATED);
			}
		} catch (Exception ex) {
			log.error("Error in UserController :: createUser() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}

	}

	@GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDTO getUserDetails(@PathVariable("userId") long userId) {
		log.info("Inside UserController :: getUserDetails()");
		try {
			Optional<User> user = userService.findUserById(userId);
			UserDTO userDTODetails = null;
			if (user.isPresent()) {
				User userDetail = user.get();
				userDTODetails = userService.convertEntityToDTO(userDetail);
				return userDTODetails;
			}
			return userDTODetails;
		} catch (Exception ex) {
			log.error("Error in UserController :: createUser() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}

	@GetMapping(value = "/userRatings/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserRatingsDetails(@PathVariable("userId") long userId) {
		log.info("Inside UserController :: getUserRatingsDetails()");
		try {
			Optional<User> user = userService.findUserById(userId);
			if (user.isPresent()) {
				List<RatingsDTO> ratingsDTOList = ratingsProxy.getUserRatings(userId);
				if (ratingsDTOList != null && (!ratingsDTOList.isEmpty())) {
					List<UserRatResDTO> res = ratingsDTOList.stream().map(ratingObj -> {

						MovieDTO movieDTO = movieProxy.getMovieDetails(ratingObj.getMovieId());
						if (movieDTO == null) {
							return new UserRatResDTO(0, "", "",
									"", 0.0f, ratingObj.getRatings(), ratingObj.getDate());
						} else {
							return new UserRatResDTO(ratingObj.getId(), movieDTO.getMovieName(), movieDTO.getOverview(),
								movieDTO.getRelaseYear(), movieDTO.getMovieRatings(), ratingObj.getRatings(), ratingObj.getDate());
						}
					}).collect(Collectors.toList());
					return new ResponseEntity<>(res, HttpStatus.OK);
				}
				return new ResponseEntity<>("User has not rated any movie", HttpStatus.OK);
			}
			return new ResponseEntity<>("User does not exists", HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error in UserController :: getUserRatingsDetails() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}

}
