/**
 * 
 */
package com.cds.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cds.dao.FileDAO;
import com.cds.model.FileModel;
import com.cds.model.ResponseMessage;
import com.cds.model.User;

/**
 * @author Jalpa.Kholiya
 * This is a service layer - Example of SpringBoot with JDBC
 * (declared as class instead of interface. ServiceImpl layer is not created for this mini project) 
 */
@Service
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	public FileDAO fileDao;

	public FileService(FileDAO fileDao) {
		this.fileDao = fileDao;
	}
	
	public ResponseMessage validateFile(MultipartFile file) {
		logger.info("Entering in validateFile()");
		ResponseMessage responseMessage = new ResponseMessage();
		BufferedReader fileReader = null;
		CSVParser csvParser = null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
			csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withIgnoreHeaderCase().withTrim());
			List<CSVRecord> csvRecords = csvParser.getRecords();
			long count = csvRecords.size();
			logger.info("CSV line count: " + count);
			
			if (count < 2) {
				responseMessage.setStatus(false);
				responseMessage.setResponseText("File must contain at least 1 record with valid header");
				logger.info("CSV header validation failed: " + responseMessage.toString());
				return responseMessage;
			}
			
			CSVRecord headerRecord = csvRecords.get(0);
			logger.info("CSV header: " + headerRecord.toString().toLowerCase());

			if (headerRecord.toString().toLowerCase().indexOf("first name") < 0 ||
					headerRecord.toString().toLowerCase().indexOf("last name") < 0 ||
					headerRecord.toString().toLowerCase().indexOf("salary") < 0 ) {
				
				responseMessage.setStatus(false);
				responseMessage.setResponseText("Required header names are missing in the file");
				logger.info("CSV header validation failed" + responseMessage.toString());
				return responseMessage;
			}
			
		} catch (IOException e) {
			throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("failed CSV validation process:  " + e.getMessage());
		} catch (NullPointerException e) {
			throw new RuntimeException("failed CSV validatio process: NULL encountered: " + e.getMessage());
		} finally {
			try {
				csvParser.close();
				fileReader.close();
			} catch (IOException e) {
				throw new RuntimeException("error while closing/flushing resources " + e.getMessage());
            }
		}
		return null;
	}
	
	public boolean saveUploadFileInfo(MultipartFile file, String clientIp){

		logger.info("Entering in saveUploadFileInfo()");
		FileModel fileModel = new FileModel();
		try {
			fileModel.setFileName(file.getOriginalFilename());
			fileModel.setSize(file.getSize());
			fileModel.setClientIp(clientIp);
			fileModel.setUploadDateTime(new Date());
			logger.info("Going to upload file details: "  + fileModel.toString());
			
			if(fileDao.insertFileInfo(fileModel)>0)
				return true;
			else 
				return false;
			
		}catch (SQLException e) {
			throw new RuntimeException("File save failed with Exception: " + e.getMessage());
		}
	}
	
	public List<FileModel> getAllFileList(){
		ArrayList<FileModel> fileList = new ArrayList<FileModel>();
		try {
			fileList = fileDao.getAllFileList();
			logger.info("total files found in the list: "+fileList.size());
			
		} catch (SQLException e) {
			throw new RuntimeException("File retrieval failed with Exception: " + e.getMessage());
		}
		return fileList;
	}
	
	public List<User> readUsersFromFile(MultipartFile file){
		logger.info("Entering in readUsersFromFile()");
		ArrayList<User> userArrList = new ArrayList<User>();
		BufferedReader fileReader = null;
		CSVParser csvParser = null;
		try {
			fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
			csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				User user = new User (); 
				user.setUserId(0);
				user.setFirstName(csvRecord.get("First Name"));
				user.setLastName(csvRecord.get("Last Name"));
				user.setSalary(Double.parseDouble(csvRecord.get("salary")));
				user.setlastUpdateTime(new Date());
				userArrList.add(user);
				logger.info("reading users: "+user.toString());				
			}
			logger.info("total users read from CSV file: "+userArrList.size());
			return userArrList;
		
		} catch (IOException e) {
			throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("failed to insert user details:  " + e.getMessage());
		} catch (NullPointerException e) {
			throw new RuntimeException("failed to parse CSV file: NULL encountered: " + e.getMessage());
		} finally {
			try {
				csvParser.close();
				fileReader.close();
			} catch (IOException e) {
				throw new RuntimeException("error while closing/flushing resources " + e.getMessage());
            }
		}
	}
}
