package com.nullcognition.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PIBroadcastReceiver extends BroadcastReceiver {

  public PIBroadcastReceiver(){
  }

  @Override
  public void onReceive(Context context, Intent intent){
	android.os.Vibrator vibrator = (android.os.Vibrator)context.getSystemService(MainActivity.VIBRATOR_SERVICE);
	vibrator.vibrate(2000);
  }
}
