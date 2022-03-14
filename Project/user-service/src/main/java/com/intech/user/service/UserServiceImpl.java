/**
 * 
 */
package com.intech.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.intech.user.dto.UserDTO;
import com.intech.user.model.User;
import com.intech.user.repository.UserRepository;

/**
 * @author Akash Budhwani
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired 
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Override
	public User saveUser(UserDTO userDTO) {
		
		User user = new User();
		user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE));
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		return userRepository.save(user);		
	}

	@Override
	public Optional<User> findUserById(Long userId) {
		
		return userRepository.findById(userId);
	}

	@Override
	public Optional<User> findUserbyEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> getAllRatings(Long userId) {
		return null;
	}
	
	@Override
	public User convertDtoToEntity(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		return user;
	}
	
	@Override
	public UserDTO convertEntityToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setEmail(user.getEmail());
		userDTO.setPassword(user.getPassword());
		return userDTO;	
	}

}
