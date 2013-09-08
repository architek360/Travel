package com.travel.me.server;
import java.util.ArrayList;
import java.util.Date;

import com.travel.me.client.entities.*;

import com.travel.me.client.TravelInfoService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TravelInfoServiceImpl extends RemoteServiceServlet implements
		TravelInfoService {

	 Objectify ofy = ObjectifyService.begin();
	  
	  //Register the Objectify Service for the Picture entity
	  static {
	    ObjectifyService.register(PersonalData.class);
	  }
	  
	public String sentInfoToServer(String name, Date timeIn, Date timeOut) throws IllegalArgumentException {
		PersonalData person = new PersonalData();
		//Use setters to populate the object
		//the Key will be auto generated and does not need to be set
		person.setName(name);
		person.setTimeIn(timeIn);
		person.setTimeOut(timeOut);
		ofy.put(person);
		return "Added " + name + " successfully";
	}

	@Override
	public ArrayList<PersonalData> getInfoFromServer() {
		Query<PersonalData> q = ofy.query(PersonalData.class);
		
		ArrayList<PersonalData> personInfoList = new ArrayList<PersonalData>();
		
		//Loop the query results and add to the array
		for (PersonalData fetched : q) {
			
			personInfoList.add(fetched);
		}
		
		log("Retreived " + personInfoList.size() + " rows");
		return personInfoList;
	}


}
