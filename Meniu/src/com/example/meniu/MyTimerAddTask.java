package com.example.meniu;

import java.util.List;
import java.util.TimerTask;

import DeviceData.Device;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MyTimerAddTask extends TimerTask{

	AddDeviceActivity appContext;
	int numberOfView;
	
	public MyTimerAddTask(AddDeviceActivity recvContext)
	{
		appContext = recvContext;
		numberOfView = 0;
	}
	
	@Override
	public void run() {
		
		
		appContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	
            	System.out.println("AM INTRAT AICI");
            	
        		
            	if(appContext.deviceInfo.size() == 0)
            		addMessageNoDevice();
            	
            	
            	
            	for(String macAddress : appContext.deviceInfo.keySet())
            			addDevice(macAddress,  appContext.deviceInfo.get(macAddress));
      		  
            	
            	
            	System.out.println("AM INTRAT AICI 2");
            }

			

			public void addMessageNoDevice() {
				
				TextView showNoDeviceMessage;
				RelativeLayout.LayoutParams params_message = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				
				showNoDeviceMessage = new TextView(appContext);
				showNoDeviceMessage.setText(R.string.noDevice);
				showNoDeviceMessage.setPadding(20, 30, 0, 0);
				showNoDeviceMessage.setTextSize(20);
				showNoDeviceMessage.setTextColor(Color.BLUE);
				showNoDeviceMessage.setId(++ numberOfView);  // devine 1

				showNoDeviceMessage.setLayoutParams(params_message);
				
				appContext.getLayout().addView(showNoDeviceMessage);
				
				
			}



			public void addDevice(String macAdress, String nameDevice) {
				
				TextView showDeviceName, showMacAdress, addOwnerDevice;
				TextView DeviceName, MacAdress;
				EditText writeOwner; 
				Button addDeviceOwnerButton;
				View line;
				
				RelativeLayout.LayoutParams params_name = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams params_Mac = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				RelativeLayout.LayoutParams params_Owner = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
								                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				
				RelativeLayout.LayoutParams nameget = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
				                                           RelativeLayout.LayoutParams.WRAP_CONTENT);
				RelativeLayout.LayoutParams macGet = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
						                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				RelativeLayout.LayoutParams ownerGet = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 
								                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				RelativeLayout.LayoutParams params_buton = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
								                                   RelativeLayout.LayoutParams.WRAP_CONTENT);
				
				
			    RelativeLayout.LayoutParams params_line = 
			    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 2);
				
				
				showDeviceName = new TextView(appContext);
				showMacAdress = new TextView(appContext);
				addOwnerDevice = new TextView(appContext);
				MacAdress = new TextView(appContext);
				DeviceName = new TextView(appContext);
				writeOwner = new EditText(appContext);
				addDeviceOwnerButton = new Button(appContext);
				
				line = new View(appContext);
				
				
				showDeviceName.setText(R.string.DeviceName);
				showDeviceName.setPadding(20, 30, 0, 0);
				showDeviceName.setTextSize(20);
				showDeviceName.setId(++ numberOfView);  // devine 1
				params_name.addRule(RelativeLayout.BELOW, numberOfView - 1 );  // fata de 0
				showDeviceName.setLayoutParams(params_name);
				
				
				DeviceName.setText(nameDevice);
				DeviceName.setPadding(40, 30, 0, 0);
				DeviceName.setTextSize(20);
				DeviceName.setId( ++ numberOfView);
				nameget.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
				nameget.addRule(RelativeLayout.BELOW, numberOfView - 2);
				DeviceName.setLayoutParams( nameget);
				
				showMacAdress.setText(R.string.MacAdress);
				showMacAdress.setPadding(20, 10, 0, 0);
				showMacAdress.setTextSize(20);	
				showMacAdress.setId( ++ numberOfView);
				params_Mac.addRule(RelativeLayout.BELOW, numberOfView - 1);
				showMacAdress.setLayoutParams(params_Mac);
				
				MacAdress.setText(macAdress);
				MacAdress.setPadding(40, 10, 0, 0);
				MacAdress.setTextSize(20);
				MacAdress.setId( ++ numberOfView);
				macGet.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
				macGet.addRule(RelativeLayout.BELOW, numberOfView - 2);
				MacAdress.setLayoutParams( macGet);
				
				addOwnerDevice.setText(R.string.addOwner);
				addOwnerDevice.setPadding(20, 10, 0, 0);
				addOwnerDevice.setTextSize(20);	
				addOwnerDevice.setId( ++ numberOfView);
				params_Owner.addRule(RelativeLayout.BELOW, numberOfView - 1);
				addOwnerDevice.setLayoutParams(params_Owner);
				
				writeOwner.setTextSize(20);
				writeOwner.setText("My Device");
				writeOwner.setId( ++ numberOfView);
				ownerGet.addRule(RelativeLayout.RIGHT_OF, numberOfView - 1);
				ownerGet.addRule(RelativeLayout.BELOW, numberOfView - 2);
				writeOwner.setLayoutParams( ownerGet);
				
				addDeviceOwnerButton.setText(R.string.addOwnerDevice);
				addDeviceOwnerButton.setTextSize(20);	
				addDeviceOwnerButton.setId( ++ numberOfView);
				params_buton.addRule(RelativeLayout.BELOW, numberOfView - 1);
				addDeviceOwnerButton.setLayoutParams(params_buton);
				
				addDeviceOwnerButton.setOnClickListener(appContext.addDevice);
				
	
				
				line.setBackgroundColor(Color.BLUE);
				line.setId( ++ numberOfView);
				params_line.addRule(RelativeLayout.BELOW, numberOfView - 1);
				line.setLayoutParams(params_line);
				
				appContext.getLayout().addView(showDeviceName);
				appContext.getLayout().addView(DeviceName);
				appContext.getLayout().addView(showMacAdress);
				appContext.getLayout().addView(MacAdress);
				appContext.getLayout().addView(addOwnerDevice);
				appContext.getLayout().addView(writeOwner);
				appContext.getLayout().addView(addDeviceOwnerButton);
				appContext.getLayout().addView(line);
				
				
			}
        });
		 
	}
	
}