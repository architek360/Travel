//This package must be a sub package of client
package com.travel.gwt.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

@SuppressWarnings("serial")
public class PersonalData implements Serializable {
	
	@Id
	Long id;
	private String name;
	private Date timeIn;
	private Date timeOut;


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}


	public Date getTimeIn() {
		return timeIn;
	}
	
	public void setTimeOut(Date timeOut) {
		this.timeOut = timeIn;
	}


	public Date getTimeOut() {
		return timeOut;
	}
	
}
