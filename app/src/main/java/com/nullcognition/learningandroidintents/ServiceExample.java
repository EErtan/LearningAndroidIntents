package com.nullcognition.learningandroidintents;
/**
 * Created by ersin on 10/10/14 at 7:33 AM
 */
public class ServiceExample extends android.app.Service {

  @Override
  public void onCreate(){
	super.onCreate();
  }

  @Override
  public int onStartCommand(android.content.Intent intent, int flags, int startId){
	return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public android.os.IBinder onBind(android.content.Intent intent){
	return null;
  }
}
