/**
 * 
 */
package com.cds.model;

/**
 * @author Jalpa.Kholiya
 * This is a response object contains status and message string
 */
public class ResponseMessage {

	private boolean status;
	private String responseText;
	
	public ResponseMessage() {
		this.status = false;
		this.responseText = null;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	@Override
	public String toString() {
		return "ResponseMessage [status=" + status + ", responseText=" + responseText + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((responseText == null) ? 0 : responseText.hashCode());
		result = prime * result + (status ? 1231 : 1237);
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
		ResponseMessage other = (ResponseMessage) obj;
		if (responseText == null) {
			if (other.responseText != null)
				return false;
		} else if (!responseText.equals(other.responseText))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	
}
