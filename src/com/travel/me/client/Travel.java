package com.travel.me.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.travel.me.client.entities.PersonalData;


public class Travel implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final TravelInfoServiceAsync travelInfoService = GWT
			.create(TravelInfoService.class);

	FlexTable setInfoFt = new FlexTable();
	FlexTable getInfoFt = new FlexTable();
	final Button sendButton = new Button("Send");
	final Button getButton = new Button("All Information");
	final TextBox nameField = new TextBox();
	final DateBox timeIn = new DateBox();
	final DateBox timeout = new DateBox();
	final Label errorLabel = new Label();
	final HTML serverResponseLabel = new HTML();
	final Image image = new Image();
	VerticalPanel inputPanel = new VerticalPanel();
	VerticalPanel displayPanel = new VerticalPanel();
	 
	public void onModuleLoad() {
		loadImage();
		RootPanel.get("main").add(inputPanel);
		addUser();
		RootPanel.get("main").add(displayPanel);
		getInformation();
	}

	private void loadImage() {
		image.setUrl("/images/loading.gif");
		image.setVisible(false);
		AbsolutePanel loadImagePanel = new AbsolutePanel ();
		loadImagePanel.add(image);
		RootPanel.get("main").add(loadImagePanel);
	}

	private void getInformation() {
		displayPanel.add(getButton);
		displayPanel.add(getInfoFt);
		getTravelInformationRpc();
	}
	
	private void getTravelInformationRpc() {
		getButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getInfoRpc();
			}
		});
	}
	
	private void getInfoRpc() {
		image.setVisible(true);
		travelInfoService.getInfoFromServer(new AsyncCallback<ArrayList<PersonalData>>() {

			@Override
			public void onFailure(Throwable caught) {
				image.setVisible(false);
		        Window.alert("Failure!");
			}
	
			public void onSuccess(ArrayList<PersonalData> result) {
				image.setVisible(false);
				int row = 1;
				getInfoFt.removeAllRows();
				getInfoFt.setText(0, 0, "Name");
				getInfoFt.setText(0, 1, "Time In");
				getInfoFt.setText(0, 2, "Time Out");
				getInfoFt.getRowFormatter().addStyleName(0,"FlexTable-Header");
				//loop the array list and user getters to add 
				//records to the table
				for (PersonalData personInfo : result) {
					try {
					row = getInfoFt.getRowCount();
					getInfoFt.setText(row, 0,
							validateTxt(personInfo.getName()));
					getInfoFt.setText(row, 1,
							validateDate(personInfo.getTimeIn()));
					getInfoFt.setText(row, 2,
							validateDate(personInfo.getTimeOut()));
					} catch (Exception e) {
						continue;
					}
					
					 HTMLTable.RowFormatter rf = getInfoFt.getRowFormatter();
					 for ( row = 1; row < getInfoFt.getRowCount(); ++row) {
					      if ((row % 2) != 0) {
					    	  rf.addStyleName(row, "FlexTable-OddRow");
					      }
					      else {
					    	  rf.addStyleName(row, "FlexTable-EvenRow");
					      }
					    }
				}
				
			}
		});
	}
	
	public String validateTxt(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		return str;
	}
	
	public String validateDate(Date date) {
		if (date == null || date.toString().length() == 0) {
			return "";
		}
		return date.toString();
	}
	
	private void addUser() {
		inputPanel.add(new Label("Enter User Information"));
		inputPanel.add(setInfoFt);
		sendButton.addStyleName("sendButton");
		nameField.setFocus(true);
		timeIn.setValue(new Date());
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
		timeIn.setFormat(new DateBox.DefaultFormat(dateFormat));
		timeIn.setFormat(new DateBox.DefaultFormat(dateFormat));
		timeout.setValue(new Date());
		timeout.setFormat(new DateBox.DefaultFormat(dateFormat));
		sendButton.addStyleName("sendButton");
		setInfoFt.setText(0, 0, "Name :");
		setInfoFt.setWidget(0, 1, nameField);
		setInfoFt.setText(1, 0, "Time In :");
		setInfoFt.setWidget(1, 1, timeIn);
		
		setInfoFt.setText(2, 0, "Time Out :");
		setInfoFt.setWidget(2, 1, timeout);
		setInfoFt.setWidget(3, 1, sendButton);
		
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending Personal information to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
				nameField.setText("");
				timeIn.setValue(new Date());
				timeout.setValue(new Date());
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			public void onClick(ClickEvent event) {
				
				sendTravelInformation();
			}

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendTravelInformation();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendTravelInformation() {
				
				image.setVisible(true);
				// First, we validate the input.
				errorLabel.setText("");
				String name = nameField.getText();
				if (name.length()  > 0 ) {
				travelInfoService.sentInfoToServer(name, timeIn.getValue(),  timeIn.getValue(),
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								image.setVisible(false);
								dialogBox
										.setText("Infomation Failed to store");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
								getInfoRpc();
								 image.setVisible(false);
							}

							public void onSuccess(String result) {
								image.setVisible(false);
								dialogBox.setText("Your infomation successfully added.");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
								getInfoRpc();
							}
						});
				} else {
					image.setVisible(false);
					Window.alert("Empty string, please enter valid name ");
				}
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
}