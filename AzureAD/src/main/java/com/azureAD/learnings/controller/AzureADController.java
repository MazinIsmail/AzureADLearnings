package com.azureAD.learnings.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.azureAD.learnings.model.UserDetails;
import com.azureAD.learnings.service.AzureADService;

@RestController
public class AzureADController {

	public static final Logger logger = LoggerFactory.getLogger(AzureADController.class);

	@Autowired
	AzureADService azureADService;

	@GetMapping(value = "/getToken")
	public void getToken() {
		logger.info("getToken()");
		String accessToken = azureADService.getAccessToken();
		logger.info("AccessToken {}", accessToken);
	}

	@GetMapping(value = "/getAllUsers")
	public void getAllUsers() {
		logger.info("getAllUsers()");
		 List<UserDetails> userList = azureADService.getAllUsers();
		 logger.info("UserList Size {}",userList.size());
	}
	
	@GetMapping(value = "/getAllUsersFromGroup/{groupId}")
	public List<UserDetails> getAllUsersFromGroup(@PathVariable String groupId) {
		logger.info("getAllUsersFromGroup()");
		 List<UserDetails> userList = azureADService.getAllUsersFromGroup(groupId);
		 logger.info("UserList Size {}",userList.size());
		 logger.info("UserList {}",userList);
		 return userList;
	}
	
	@GetMapping(value = "/getSpecificUser/{userId}")
	public UserDetails getSpecificUser(@PathVariable String userId) {
		logger.info("getSpecificUser()");
		//userid - Sneha@unnikrishnannairpoutlook.onmicrosoft.com
		UserDetails user = azureADService.getSpecificUser(userId);
		logger.info("User {}", user);
		return user;
	}

	
	@GetMapping(value = "/get")
	public void getData() {
		logger.info("getSpecificUser()");
		//userid - Sneha@unnikrishnannairpoutlook.onmicrosoft.com
		String accessToken = azureADService.getAccessToken();
		String urlData = "https://graph.microsoft.com/v1.0/education/schools";
		String user = azureADService.getDataFromURL(urlData, accessToken);
		logger.info("User {}", user);
	}

}
