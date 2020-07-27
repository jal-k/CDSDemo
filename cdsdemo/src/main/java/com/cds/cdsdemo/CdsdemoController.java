/**
 * 
 */
package com.cds.cdsdemo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.cds.model.FileModel;
import com.cds.model.ResponseMessage;
import com.cds.model.User;
import com.cds.service.FileService;
import com.cds.service.UserService;
import com.cds.utils.Constants;

/**
 * @author Jalpa.Kholiya
 * Main controller which serves requests
 */
@RequestMapping("/users")
@RestController
public class CdsdemoController {
	
	private static final Logger logger = LoggerFactory.getLogger(CdsdemoController.class);
	
	@Autowired
	private final UserService userService;
	
	@Autowired
	private final FileService fileService;
	
	@Autowired
    public CdsdemoController(UserService userService, FileService fileService) {
		this.userService = userService;
		this.fileService = fileService;
	}
 
    @RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
    	logger.info("Request received in CdsdemoController.getUsers()");
    	return userService.getAllUserDetails();
    }
    
    @RequestMapping(value = Constants.GET_ALL_USERS,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
    	logger.info("Request received in CdsdemoController.getUsers()");
    	return userService.getAllUserDetails();
    }
    
    @RequestMapping(value = Constants.UPLOAD_FILE, method = RequestMethod.POST)
    public ResponseMessage uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("ip") String ClientIp) {
    	logger.info("Request received in CdsdemoController.uploadFile() from client ip "+ ClientIp);

    	ResponseMessage responseMessage = fileService.validateFile(file);
    	if(responseMessage != null) {
    		return responseMessage;
    	}else
    		responseMessage = new ResponseMessage();
    	
    	if(!fileService.saveUploadFileInfo(file, ClientIp)) {
    		responseMessage.setStatus(false);
    		responseMessage.setResponseText("Failed to save file");
    		return responseMessage;
    	}
    	if(userService.saveUsersFromFile((ArrayList<User>)fileService.readUsersFromFile(file))) {
    		responseMessage.setStatus(true);
    		responseMessage.setResponseText("File uploaded successfully");
    	}else {
    		responseMessage.setStatus(false);
    		responseMessage.setResponseText("Failed to upload file");
    	}
    	return responseMessage;
    }
    
    @RequestMapping(value = Constants.GET_ALL_FILES,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileModel> getAllFiles() {
    	logger.info("Request received in CdsdemoController.getAllFiles()");
    	return fileService.getAllFileList();
    }
    
    @RequestMapping(Constants.GET_USER_BY_ID)
    public User getUserById(@PathVariable Integer userId) {
    	logger.info("Request received in CdsdemoController.getUserById() for user ID: "+userId);
    	return userService.getUserDetailsById(userId);
    }
        
    @RequestMapping(value = Constants.GET_USER_BY_SALARY,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUserBySalary(@RequestParam("salarylow") double salarylow, @RequestParam("salaryhigh") double salaryhigh) {
    	logger.info("Request received in CdsdemoController.getUserBySalary()");
    	return userService.getUserBySalary(salarylow,salaryhigh);
    }
    
}
