package com.nullcognition.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LowBattery extends BroadcastReceiver {

  public LowBattery(){ }

  @Override
  public void onReceive(Context inContext, Intent intent){

	final Context context = inContext;

	if(intent.getAction() == android.content.Intent.ACTION_BATTERY_LOW){

	  Thread thread = new Thread(new Runnable() {

		@Override
		public void run(){

		  FragmentAlertDialog fragmentAlertDialog = FragmentAlertDialog
			.newInstance(com.nullcognition.app2.R.id.action_settings, com.nullcognition.app2.R.id.action_settings,
						 com.nullcognition.app2.R.id.action_settings, com.nullcognition.app2.R.id.action_settings);

		  try{
			final android.app.Activity activity = (android.app.Activity)context;

			// Return the fragment manager

			// If using the Support lib.
			// return activity.getSupportFragmentManager();
			fragmentAlertDialog.show(activity.getFragmentManager(), "dia"); // carefull with this kind of code

		  }
		  catch(ClassCastException e){
			android.util.Log.d("a", "Can't get the fragment manager with this");
		  }

		  // possible to change state ui from outside of the class, posting to the ui thead is not allowed
		  // look into the details

		}
	  });
	  thread.start(); // thread used since broadcastReceiver life time is so small
	}
  }
}
