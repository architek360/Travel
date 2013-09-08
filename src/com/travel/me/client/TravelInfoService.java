package com.travel.me.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.travel.me.client.entities.PersonalData;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface TravelInfoService extends RemoteService {
	String sentInfoToServer(String name, Date timeIn, Date timeOut) throws IllegalArgumentException;
	ArrayList<PersonalData> getInfoFromServer();
	
}
