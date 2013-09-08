package com.travel.me.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.travel.me.client.entities.PersonalData;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface TravelInfoServiceAsync {
	void sentInfoToServer(String input,Date timeIn, Date timeOut, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	
	void getInfoFromServer(AsyncCallback<ArrayList<PersonalData>> callback)
			throws IllegalArgumentException;

}
