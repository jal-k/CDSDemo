/**
 * 
 */
package com.cds.model;

import java.util.Date;

/**
 * @author Jalpa.Kholiya
 *
 */
public class FileModel {
	
	private String fileName;
	private Long size;
	private String clientIp;
	private Date uploadDateTime;
	
	public FileModel() {
		this.fileName = null;
		this.size = null;
		this.clientIp=null;
		this.uploadDateTime=new Date();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	@Override
	public String toString() {
		return "FileModel [fileName=" + fileName + ", size=" + size + ", clientIp=" + clientIp + ", uploadDateTime="
				+ uploadDateTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientIp == null) ? 0 : clientIp.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((uploadDateTime == null) ? 0 : uploadDateTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileModel other = (FileModel) obj;
		if (clientIp == null) {
			if (other.clientIp != null)
				return false;
		} else if (!clientIp.equals(other.clientIp))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (uploadDateTime == null) {
			if (other.uploadDateTime != null)
				return false;
		} else if (!uploadDateTime.equals(other.uploadDateTime))
			return false;
		return true;
	}
	
	
}
