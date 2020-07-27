/**
 * 
 */
package com.cds.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cds.model.FileModel;

/**
 * @author Jalpa.Kholiya
 * This is a DAO class - Example of SpringBoot with JDBC
 * (declared as class instead of interface. DAOImpl layer is not created for this mini project) 
 */
@Repository
public class FileDAO {

	private static final Logger logger = LoggerFactory.getLogger(FileDAO.class);
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private String INSERT_UPLOAD_FILE_INFO =  "insert into cds.cds_upload_file_info (FILE_NAME,SIZE,CLIENT_IP,UPLOAD_DATE_TIME) " +
												"values (?,?,?,?)";
	private String SELECT_UPLOAD_FILE_LIST = "select * from cds.cds_upload_file_info order by UPLOAD_DATE_TIME";
	
	public int insertFileInfo(FileModel fileModel) throws SQLException{
		if (fileModel!=null) {
			return jdbcTemplate.update(INSERT_UPLOAD_FILE_INFO,fileModel.getFileName(),fileModel.getSize(),fileModel.getClientIp(),fileModel.getUploadDateTime());
		}
		else 
			return 0;
	}	
	
	public ArrayList<FileModel> getAllFileList() throws SQLException{
		ArrayList<FileModel> fileList = new ArrayList<FileModel>();
		
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_UPLOAD_FILE_LIST);
        logger.info("SELECT_UPLOAD_FILE_LIST: rows returned "+rows.size());
        
        for (Map<String, Object> row : rows) 
        {
        	FileModel fileModel = new FileModel();
        	fileModel.setFileName((String)row.get("file_name"));
        	fileModel.setSize((Long)row.get("size"));
        	fileModel.setClientIp((String)row.get("client_ip"));
        	fileModel.setUploadDateTime((Date)row.get("upload_date_time"));
        	fileList.add(fileModel);
         }
        return fileList;
	}
}
