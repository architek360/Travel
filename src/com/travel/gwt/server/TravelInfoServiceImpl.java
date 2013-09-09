package com.travel.gwt.server;
import java.util.ArrayList;
import java.util.Date;

import com.travel.gwt.client.travelinfo.TravelInfoService;
import com.travel.gwt.shared.PersonalData;
import com.travel.server.common.MyUtil;
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
		person.setName(name);
		person.setTimeIn(MyUtil.getDateWithoutTimeComponent(timeIn));
		person.setTimeOut(MyUtil.getDateWithoutTimeComponent(timeOut));
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
