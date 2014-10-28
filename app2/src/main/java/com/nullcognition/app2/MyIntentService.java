package com.nullcognition.app2;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class MyIntentService extends IntentService {


  private static final int NOTIFICATION_ID = 1;
  android.app.NotificationManager notificationManager;
  android.app.Notification        notification;
  public static final String ACTION_CUSTOM_INTENT_SERVICE = "com.app.intentservice.RESPONSE";
  public static final String ACTION_MY_UPDATE             = "com.app.intentservice.UPDATE";
  public static final String EXTRA_KEY_IN                 = "EXTRA_IN";
  public static final String EXTRA_KEY_OUT                = "EXTRA_OUT";
  public static final String EXTRA_KEY_UPDATE             = "EXTRA_UPDATE";
  String activityMessage;
  String extraOut;

  // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
  private static final String ACTION_FOO = "com.nullcognition.app2.action.FOO";
  private static final String ACTION_BAZ = "com.nullcognition.app2.action.BAZ";

  private static final String EXTRA_PARAM1 = "com.nullcognition.app2.extra.PARAM1";
  private static final String EXTRA_PARAM2 = "com.nullcognition.app2.extra.PARAM2";

  /**
   * Starts this service to perform action Foo with the given parameters. If
   * the service is already performing a task this action will be queued.
   *
   * @see IntentService
   */
  public static void startActionFoo(Context context, String param1, String param2){
	Intent intent = new Intent(context, MyIntentService.class);
	intent.setAction(ACTION_FOO);
	intent.putExtra(EXTRA_PARAM1, param1);
	intent.putExtra(EXTRA_PARAM2, param2);
	context.startService(intent);
  }

  /**
   * Starts this service to perform action Baz with the given parameters. If
   * the service is already performing a task this action will be queued.
   *
   * @see IntentService
   */
  public static void startActionBaz(Context context, String param1, String param2){
	Intent intent = new Intent(context, MyIntentService.class);
	intent.setAction(ACTION_BAZ);
	intent.putExtra(EXTRA_PARAM1, param1);
	intent.putExtra(EXTRA_PARAM2, param2);
	context.startService(intent);
  }

  public MyIntentService(){
	super("MyIntentService");
  }

  @Override
  public void onCreate(){
	super.onCreate();
	notificationManager = (android.app.NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
  }

  @Override
  protected void onHandleIntent(Intent intent){
	if(intent != null){
	  final String action = intent.getAction();
	  if(ACTION_FOO.equals(action)){
		final String param1 = intent.getStringExtra(EXTRA_PARAM1);
		final String param2 = intent.getStringExtra(EXTRA_PARAM2);
		handleActionFoo(param1, param2);
	  }
	  else if(ACTION_BAZ.equals(action)){
		final String param1 = intent.getStringExtra(EXTRA_PARAM1);
		final String param2 = intent.getStringExtra(EXTRA_PARAM2);
		handleActionBaz(param1, param2);
	  }
	}

	//get input
	activityMessage = intent.getStringExtra(EXTRA_KEY_IN);
	extraOut = "Hello: " + activityMessage;
	for(int i = 0; i <= 10; i++){
	  try{
		Thread.sleep(1000);
	  }
	  catch(InterruptedException e){
		e.printStackTrace();
	  }
//send update
	  Intent intentUpdate = new Intent();
	  intentUpdate.setAction(ACTION_MY_UPDATE);
	  intentUpdate.addCategory(Intent.CATEGORY_DEFAULT);
	  intentUpdate.putExtra(EXTRA_KEY_UPDATE, i);
	  sendBroadcast(intentUpdate);
//generate notification
	  String notificationText = String.valueOf((int)(100 * i / 10)) + " %";
	  notification = new android.support.v4.app.NotificationCompat.Builder(getApplicationContext()).setContentTitle("Progress")
																								   .setContentText(notificationText)
																								   .setTicker("Notification!")
																								   .setWhen(System.currentTimeMillis()).setDefaults(
		  android.app.Notification.DEFAULT_SOUND).setAutoCancel(true).setSmallIcon(R.drawable.ic_launcher).build();
	  notificationManager.notify(NOTIFICATION_ID, notification);

	}

	//return result
	Intent intentResponse = new Intent();
	intentResponse.setAction(ACTION_CUSTOM_INTENT_SERVICE);
	intentResponse.addCategory(Intent.CATEGORY_DEFAULT);
	intentResponse.putExtra(EXTRA_KEY_OUT, extraOut);
	sendBroadcast(intentResponse);
  }

  /**
   * Handle action Foo in the provided background thread with the provided
   * parameters.
   */

  private void handleActionFoo(String param1, String param2){
	throw new UnsupportedOperationException("Not yet implemented");
  }

  /**
   * Handle action Baz in the provided background thread with the provided
   * parameters.
   */
  private void handleActionBaz(String param1, String param2){
	throw new UnsupportedOperationException("Not yet implemented");
  }
}
