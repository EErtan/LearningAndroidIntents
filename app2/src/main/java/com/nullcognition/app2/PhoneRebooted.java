package com.nullcognition.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PhoneRebooted extends BroadcastReceiver {

  public PhoneRebooted(){
  }

  @Override
  public void onReceive(Context inContext, Intent intent){

	final Context context = inContext;

	if(intent.getAction() == android.content.Intent.ACTION_BOOT_COMPLETED){

	  Intent serviceIntent = new android.content.Intent(context, MyService.class);
	  context.startService(serviceIntent);

	}
  }
}
