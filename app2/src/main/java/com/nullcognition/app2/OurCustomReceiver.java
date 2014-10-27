package com.nullcognition.app2;
/**
 * Created by ersin on 27/10/14 at 12:07 AM
 */
public class OurCustomReceiver extends android.content.BroadcastReceiver {

  @Override
  public void onReceive(android.content.Context context, android.content.Intent intent){
	if(intent.getAction() == "com.nullocgnition.anything.myActionToBeBroadcast"){
	  android.widget.Toast.makeText(context, "Broadcast Intent Detected.", android.widget.Toast.LENGTH_LONG).show();
	}
  }

}
