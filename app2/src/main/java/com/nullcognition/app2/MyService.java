package com.nullcognition.app2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

  @Override
  public int onStartCommand(android.content.Intent intent, int flags, int startId){

	android.widget.Toast.makeText(getBaseContext(), "Phone Rebooted From service", android.widget.Toast.LENGTH_SHORT).show();

	return START_STICKY;
  }

  public MyService(){ }

  @Override
  public IBinder onBind(Intent intent){return null;}


}
