package com.cds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cds.model.User;
import com.cds.repository.UserRepository;

/**
 * @author Jalpa.Kholiya
 * This is a service layer - Example of SpringBoot with CrudRepository
 * (declared as class instead of interface. ServiceImpl layer is not created for this mini project) 
 */
@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public UserService() {
	}

	public boolean saveUsersFromFile(ArrayList<User> userList) {
		logger.info("Entering in saveUsersFromFile()");
		try {
			Iterable<User> usersInserted = userRepository.saveAll(userList);
			
			for(User user: usersInserted) {
				return true;
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("failed to insert user details:  " + e.getMessage());
		} 
		return false;
	}

	public List<User> getAllUserDetails() {
		logger.info("Entering in getAllUserDetails()");
		ArrayList<User> userList = new ArrayList<User>();

		Iterable<User> users= userRepository.findAll();
		for (User user : users) {
			userList.add(user);
		}
		logger.info("Total users returned: " + userList.size());
		return userList;
	}

	public User getUserDetailsById(int userId) {
		logger.info("Entering in getUserDetailsById() for user ID: " + userId);
		User user = new User();
		Optional<User> resultUser = userRepository.findById(userId);
		if(resultUser.isPresent()) {
			user = resultUser.get();
			logger.info("user found : " + user.toString());
			return user;
		}else
			return null;
	}

	public List<User> getUserBySalary(double salarylow, double salaryhigh) {
		logger.info("Entering in getUserDetailsById() for salary range: " + salarylow + "To" + salaryhigh);
		List<User> userList = new ArrayList<User>();
		userList = userRepository.findUsersForSalaryRange(salarylow, salaryhigh);
		if(userList.size()>0) {
			logger.info("Total users returned: " + userList.size());
		}
		return userList;
	}

}
