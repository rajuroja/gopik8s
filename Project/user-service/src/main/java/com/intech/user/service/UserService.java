/**
 * 
 */
package com.intech.user.service;

import java.util.List;
import java.util.Optional;

import com.intech.user.dto.UserDTO;
import com.intech.user.model.User;

/**
 * @author Akash Budhwani
 *
 */
public interface UserService {
	
	public User saveUser(UserDTO userDTO);
	
	public Optional<User> findUserById(Long userId);
	
	public Optional<User> findUserbyEmail(String email);
	
	public List<User> getAllRatings(Long userId);

	User convertDtoToEntity(UserDTO userDTO);

	UserDTO convertEntityToDTO(User user);
}
