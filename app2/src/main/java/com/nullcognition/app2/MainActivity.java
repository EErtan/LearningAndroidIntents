package com.nullcognition.app2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements com.nullcognition.app2.FragmentAlertDialog.OnFragmentAlertDialogInteractionListener {

  private int pendingIntentRequestCode = 111;

  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	broadcastLowBattery();

	sendCustomBroadCast();

	usingPendingIntent();
  }

  private void usingPendingIntent(){

	android.content.Intent intent = new android.content.Intent(this, PIBroadcastReceiver.class);
	android.app.PendingIntent pendingIntent = android.app.PendingIntent.getBroadcast(this, pendingIntentRequestCode, intent, 0);

	android.app.AlarmManager alarmManager = (android.app.AlarmManager)getSystemService(android.app.Activity.ALARM_SERVICE);
	alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, 1000, pendingIntent);
  }


  com.nullcognition.app2.LowBattery lowBattery;

  private void broadcastLowBattery(){
	android.content.IntentFilter intentFilter = new android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_LOW);
	lowBattery = new LowBattery();
	registerReceiver(lowBattery, intentFilter); // this is neat, assignment in the method parameter
  }

  public void sendCustomBroadCast(){
	android.content.Intent intent = new android.content.Intent();
	intent.setAction("com.nullocgnition.anything.myActionToBeBroadcast"); // note that anything works
	// as long as it matches in the manifest and in the receiver
	sendBroadcast(intent);
  }

  @Override
  protected void onPause(){
	super.onPause();
	unregisterReceiver(lowBattery); // if done in manifest level then no need to unreg
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.menu_main, menu);
	return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item){
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();

	//noinspection SimplifiableIfStatement
	if(id == R.id.action_settings){

	  // compile 'com.android.support:support-v4:21.0.0'
	  android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(this)
		.setSmallIcon(com.nullcognition.app2.R.drawable.ic_launcher).setContentTitle("My notification").setContentText("Hello World!");
	  android.content.Intent resultIntent = new android.content.Intent(this, com.nullcognition.app2.MainActivity.class);

	  android.app.TaskStackBuilder stackBuilder = android.app.TaskStackBuilder.create(this);
	  stackBuilder.addParentStack(com.nullcognition.app2.MainActivity.class);
	  stackBuilder.addNextIntent(resultIntent);
	  android.app.PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
	  mBuilder.setContentIntent(resultPendingIntent);
	  android.app.NotificationManager mNotificationManager = (android.app.NotificationManager)getSystemService(
		android.content.Context.NOTIFICATION_SERVICE);
	  mNotificationManager.notify(18, mBuilder.build());

	  sendBroadcast(new android.content.Intent(android.content.Intent.ACTION_BATTERY_LOW));

	  return true;
	}

	return super.onOptionsItemSelected(item);
  }

  @Override
  public void onAlertDialogFragmentInteraction(int buttonClicked){

  }
}
