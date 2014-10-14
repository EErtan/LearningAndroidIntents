package com.nullcognition.learningandroidintents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkStatusReceiver extends BroadcastReceiver {

  public NetworkStatusReceiver(){
  }

  @Override
  public void onReceive(Context context, Intent intent){

	android.widget.Toast.makeText(context, "Network connectivity changed", android.widget.Toast.LENGTH_SHORT).show();

	android.os.Bundle extras = intent.getExtras();
	boolean noNetwork = intent.getBooleanExtra(android.net.ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

	if(extras != null){

	  String networkInfoString = android.net.ConnectivityManager.EXTRA_OTHER_NETWORK_INFO;
	  android.net.NetworkInfo networkInfo = (android.net.NetworkInfo)extras.get(networkInfoString);

	  if(networkInfo != null && networkInfo.getState() == android.net.NetworkInfo.State.CONNECTED){
		android.widget.Toast.makeText(context, "Network type: " + networkInfo.getTypeName() + " connected", android.widget.Toast.LENGTH_SHORT).show();
	  }
	  else if(noNetwork){
		android.widget.Toast.makeText(context, "No networks available", android.widget.Toast.LENGTH_SHORT).show();
	  }

	}
  }
}
