package com.nullcognition.learningandroidintents;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityMain extends Activity {

  public final int blueToothDiscoverableCode = 1001;
  int blueToothRequestCode = 101;
  int speechRequestCode    = 123;
  com.nullcognition.learningandroidintents.BluetoothStateReceiver btsr;

  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	{
	  final String SMS_RECEIVED = "android.provider.android.provider.Telephony.SMS_RECEIVED";
	  android.content.IntentFilter filter = new android.content.IntentFilter(SMS_RECEIVED);
	  android.content.BroadcastReceiver broadcastReceiver = new com.nullcognition.learningandroidintents.ActivityMain.IncomingMsgReceiver();
	  registerReceiver(broadcastReceiver, filter);
	}

	android.widget.Button button = (android.widget.Button)findViewById(com.nullcognition.learningandroidintents.R.id.button);
	button.setOnClickListener(new android.view.View.OnClickListener() {

	  @Override
	  public void onClick(android.view.View inView){
		android.content.Intent intentToAcivityFrom = new android.content.Intent(getApplicationContext(), ActivityFromIntent.class);
		intentToAcivityFrom.putExtra("intentToAcivityFrom", "value");
		startActivityForResult(intentToAcivityFrom, 1);
	  }
	});

	android.widget.Button button2 = (android.widget.Button)findViewById(com.nullcognition.learningandroidintents.R.id.takePic);
	button2.setOnClickListener(new android.view.View.OnClickListener() {

	  @Override
	  public void onClick(android.view.View inView){
		android.content.Intent startServ = new android.content.Intent(getApplicationContext(),
																	  com.nullcognition.learningandroidintents.ServiceExample.class);
		startServ.putExtra("startServ", "value");
		startService(startServ);
	  }
	});
	button2.setOnLongClickListener(new android.view.View.OnLongClickListener() {

	  @Override
	  public boolean onLongClick(android.view.View v){
		android.content.Intent stopSer = new android.content.Intent(getApplicationContext(),
																	com.nullcognition.learningandroidintents.ServiceExample.class);
		stopSer.putExtra("stopSer", "value");
		stopService(stopSer);
		return true;
	  }
	});

	android.widget.Button wifi = (android.widget.Button)findViewById(com.nullcognition.learningandroidintents.R.id.wifi);
	wifi.setOnClickListener(new android.view.View.OnClickListener() {

	  @Override
	  public void onClick(android.view.View inView){
		android.content.Intent wifi = new android.content.Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
		wifi.putExtra("wifi", "value");
		startActivity(wifi);
	  }
	});

	android.widget.Button buttonVoiceRecognition = (android.widget.Button)findViewById(
	  com.nullcognition.learningandroidintents.R.id.buttonVoiceRecognition);
	buttonVoiceRecognition.setOnClickListener(new android.view.View.OnClickListener() {

	  @Override
	  public void onClick(android.view.View inView){
		android.content.Intent i = new android.content.Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		i.putExtra(android.speech.RecognizerIntent.EXTRA_PROMPT, "Speak");
		i.putExtra(android.speech.RecognizerIntent.EXTRA_MAX_RESULTS, 1);
		i.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, java.util.Locale.ENGLISH);
		startActivityForResult(i, speechRequestCode); // active internet connection is required
	  }
	});

	android.widget.Button standardIntent = (android.widget.Button)findViewById(com.nullcognition.learningandroidintents.R.id.standardIntent);
	standardIntent.setOnClickListener(new android.view.View.OnClickListener() {

	  @Override
	  public void onClick(android.view.View inView){
		android.content.Intent intent = new android.content.Intent(getApplicationContext(), ActivityFromIntent.class);
		intent.putExtra("string1", "string1");

		Bundle bundle = new android.os.Bundle();
		bundle.putString("string2", "string2");
		Parc p = new com.nullcognition.learningandroidintents.ActivityMain.Parc();
		bundle.putParcelable("parc", p);
		intent.putExtras(bundle);

		startActivity(intent);
	  }
	});

	checkForTTS();

	initBluetooth();

	blueToothDiscoverable();

	proximityAlert();
  }

  public void implicitIntents(){
	float latitude = 3.32437f, logitude = 3.473f;
	String uri = "geo:" + latitude + "," + logitude;
	startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(uri))); // google maps

	String url = "http://www.google.com";
	startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))); // browser

	android.content.Intent email = new android.content.Intent(android.content.Intent.ACTION_SEND);
	email.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"john@domain.com"}); // recipients
	email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Email subject");
	email.putExtra(android.content.Intent.EXTRA_TEXT, "message body");
	startActivity(email);

	android.net.Uri numberToCall = android.net.Uri.parse("tel:5555555");
	startActivity(new android.content.Intent(android.content.Intent.ACTION_DIAL, numberToCall)); // also can use ACTION_CALL, but with restrictions
	// <uses-premission android:name="android.permission.CALL_PHONE"/>

	android.content.Intent smsIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
	smsIntent.putExtra("smsbody", "my sms text");
	smsIntent.setType("image/png");
	startActivity(smsIntent); // since sms(short message service) and mms (multimedia message service) are not supported by default, you must
	// use in implicit intent,

	String phoneNum = "sms:" + "5555555555";
	android.net.Uri phoneNumUri = android.net.Uri.parse(phoneNum);
	android.content.Intent smsInt = new android.content.Intent(android.content.Intent.ACTION_SENDTO,
															   phoneNumUri); // using sendto and including the sms: as part
	// of the uri you can use sms supported applications to send the data, to specify the recipient
	smsInt.putExtra("smsbody", "text");
	startActivity(smsInt);

	android.net.Uri myMediaUri = android.net.Uri.parse("content://media/external/images/somefile");
	android.content.Intent mms = new android.content.Intent(android.content.Intent.ACTION_SEND);
	mms.putExtra("mmsbody", "text");
	mms.setType("image/png");
	mms.putExtra(android.content.Intent.EXTRA_STREAM, myMediaUri);
	startActivity(mms);
  }

  public void smsMan(){

	// <uses-permission android:name="android.permission.SEND_SMS"/>

	registerReceiver(new android.content.BroadcastReceiver() {

	  @Override
	  public void onReceive(android.content.Context context, android.content.Intent intent){
		if(getResultCode() == android.app.Activity.RESULT_OK){
		  // sent succesfully
		}
		else{}//pending...
	  }
	}, new android.content.IntentFilter("sent_sms_action"));

	registerReceiver(new android.content.BroadcastReceiver() {

	  @Override
	  public void onReceive(android.content.Context context, android.content.Intent intent){
		// delivered so update ui or notify
	  }
	}, new android.content.IntentFilter("delivered_sms_action"));

	// sent intent
	android.content.Intent sentIntent = new android.content.Intent("sent_sms_action");
	android.app.PendingIntent sentPendingIntent = android.app.PendingIntent.getBroadcast(getApplicationContext(), 00, sentIntent, 0);

	// delivery intent
	android.content.Intent deliveryIntent = new android.content.Intent("delivered_sms_action");
	android.app.PendingIntent deliveredPendingIntent = android.app.PendingIntent.getBroadcast(getApplicationContext(), 0, deliveryIntent, 0);

	android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
	smsManager.sendTextMessage("tel:5555555555", null, " heleeeeeoooooooooooooo", sentPendingIntent, deliveredPendingIntent);

  }

  public class IncomingMsgReceiver extends android.content.BroadcastReceiver {

	private static final String SMS_RECEIVED = "android.provider.android.provider.Telephony.SMS_RECEIVED"; // unsupported hidden message

	public void onReceive(android.content.Context _context, android.content.Intent _intent){
	  if(_intent.getAction().equals(SMS_RECEIVED)){
		Bundle msgBundle = _intent.getExtras();
		getMessageData(msgBundle); // use ddms -> emulator control tab to send a sms via incomming number
	  }
	}
  }


  private void getMessageData(android.os.Bundle inMsgBundle){
	if(inMsgBundle != null){
	  Object[] p = (Object[])inMsgBundle.get("pdus");
	  android.telephony.SmsMessage[] messages = new android.telephony.SmsMessage[p.length];
	  for(int i = 0; i < p.length; ++ i){
		messages[i] = android.telephony.SmsMessage.createFromPdu((byte[])p[i]);
		for(android.telephony.SmsMessage m : messages){
		  String mes = m.getMessageBody();
		  String to = m.getOriginatingAddress();
		  String time = Long.toString(m.getTimestampMillis());
		}

	  }
	}
  }


  static class Parc implements android.os.Parcelable,// may be used to transfer single or array of parcels
							   java.io.Serializable { // for serializable example

	int    i = 1;
	String s = "s";

	public Parc(){}

	public Parc(android.os.Parcel inParcel){
	  i = inParcel.readInt();
	  s = inParcel.readString();
	}

	public void setS(String inS){ s = inS; }

	public void setI(int inI){ i = inI; }

	public String getS(){ return s; }

	public int getI(){ return i; }


	@Override
	public int describeContents(){
	  return 0;
	}

	@Override
	public void writeToParcel(android.os.Parcel dest, int flags){
	  dest.writeString(s);
	  dest.writeInt(i);
	}
  }

  public void serializeMe(){ // converts the object to a byte array
	Parc parc = new com.nullcognition.learningandroidintents.ActivityMain.Parc();
	parc.setI(5);
	parc.setS("string");
	try{
	  java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream("/tmp/serializedObject.ser");
	  java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(fileOutputStream);
	  objectOutputStream.writeObject(parc);
	  objectOutputStream.close();
	  fileOutputStream.close();
	}
	catch(java.io.IOException e){e.printStackTrace();}
  }

  public void deserializeMe(){
	Parc parc = null;
	try{
	  java.io.FileInputStream fileInputStream = new java.io.FileInputStream("/tmp/serializedObject.ser");
	  java.io.ObjectInputStream objectInputStream = new java.io.ObjectInputStream(fileInputStream);
	  parc = (Parc)objectInputStream.readObject();
	  objectInputStream.close();
	  fileInputStream.close();
	}
	catch(java.io.IOException e){e.printStackTrace();}
	catch(ClassNotFoundException c){c.printStackTrace();}
  }

  private void proximityAlert(){

	String DistanceAlert = "distance.proximity.alert";
	String locationService = android.content.Context.LOCATION_SERVICE;
	android.location.LocationManager lm;
	lm = (android.location.LocationManager)getSystemService(locationService);

	double lat = 23.234; // set these to be dynamic based on the users location and you have a field around you
	double longi = 34.432;
	float radius = 45f;
	long expire = - 1;

	android.content.Intent intent = new android.content.Intent(DistanceAlert);
	android.app.PendingIntent pendingIntent = android.app.PendingIntent.getBroadcast(this, - 1, intent, 0);
	lm.addProximityAlert(lat, longi, radius, expire, pendingIntent);

	//registar for receiver, dynamic registration
	android.content.IntentFilter intentFilter = new android.content.IntentFilter(DistanceAlert);
	registerReceiver(new com.nullcognition.learningandroidintents.ActivityMain.ProximityAlertReceiver(), intentFilter);

  }

  public static class ProximityAlertReceiver extends android.content.BroadcastReceiver {

	@Override
	public void onReceive(android.content.Context context, android.content.Intent intent){
	  Boolean isEntered = intent.getBooleanExtra(android.location.LocationManager.KEY_PROXIMITY_ENTERING, false);
	  if(isEntered){ android.widget.Toast.makeText(context, "Device has Entered!", android.widget.Toast.LENGTH_SHORT).show(); }
	  else{ android.widget.Toast.makeText(context, "Device has Left!", android.widget.Toast.LENGTH_SHORT).show(); }
	}
  }


  final int valttsdata = 14;

  private void checkForTTS(){
	// check for text to speech
	android.content.Intent i = new android.content.Intent(android.speech.tts.TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	startActivityForResult(i, valttsdata);
  }

  @Override
  protected void onPause(){
	super.onPause();
	unregisterReceiver(btsr);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_main, menu);
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
	  android.content.Intent in = new android.content.Intent(android.content.Intent.ACTION_SEND);
	  in.putExtra(android.content.Intent.EXTRA_TEXT, "abc");
	  in.setType("text/html"); // image/png image/*
	  startActivity(android.content.Intent.createChooser(in, "Share via...")); // choose possible activities to use
	  return true;
	}
	if(id == R.id.action_notification){

	  return true;
	}

	return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data){
	super.onActivityResult(requestCode, resultCode, data);

	// look into the new way of dealing with this tts from api 21
//
//	if(requestCode == valttsdata && resultCode == android.speech.tts.TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
//	  final android.speech.tts.TextToSpeech tts = new android.speech.tts.TextToSpeech(this, new android.speech.tts.TextToSpeech.OnInitListener() {
//
//		public void onInit(int status){
//		  if(status == android.speech.tts.TextToSpeech.SUCCESS){
//			tts.setLanguage(java.util.Locale.US);
//			tts.setSpeechRate(1.1f);
//			tts.speak("Hello, I am writing book for Packt", android.speech.tts.TextToSpeech.QUEUE_ADD, null,null);
//		  }
//		}
//	  });
//	}
//	else{
//	  android.widget.Toast.makeText(this, "no tts available", android.widget.Toast.LENGTH_SHORT).show();
//	  android.content.Intent installLanguage = new android.content.Intent(android.speech.tts.TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//	  startActivity(installLanguage);
//	}

	if(requestCode == speechRequestCode && resultCode == android.app.Activity.RESULT_OK){
	  java.util.ArrayList<String> results;
	  results = data.getStringArrayListExtra(android.speech.RecognizerIntent.EXTRA_RESULTS);
	  android.widget.EditText et = (android.widget.EditText)findViewById(com.nullcognition.learningandroidintents.R.id.editText);
	  et.setText(results.toString());
	}
	if(requestCode == 1 && resultCode == RESULT_OK){
	  android.widget.Toast.makeText(this, "ok", android.widget.Toast.LENGTH_SHORT).show();
	  //new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("http://www.google.ca/"));
	}
	else if(requestCode == blueToothRequestCode && resultCode == android.app.Activity.RESULT_OK){
	  android.widget.Toast.makeText(this, "BLUETOOTH ACTIVATED!", android.widget.Toast.LENGTH_SHORT).show();
	}
	else if(requestCode == blueToothDiscoverableCode && resultCode == android.app.Activity.RESULT_OK){
	  android.widget.Toast.makeText(this, "BLUETOOTH Discovered!", android.widget.Toast.LENGTH_SHORT).show();
	}

	if(resultCode == RESULT_CANCELED){
	  android.widget.Toast.makeText(this, "not ok", android.widget.Toast.LENGTH_SHORT).show();
	}
  }

  private void initBluetooth(){
	btsr = new BluetoothStateReceiver();
	registerReceiver(btsr, new android.content.IntentFilter(android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE));

	String enableBluetooth = android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE;
	android.content.Intent intentBluetooth = new android.content.Intent(enableBluetooth);
	startActivityForResult(intentBluetooth, blueToothRequestCode);
  }

  private void blueToothDiscoverable(){

	registerReceiver(new android.content.BroadcastReceiver() {

	  @Override
	  public void onReceive(android.content.Context context, android.content.Intent intent){

		String prevStateExtra = android.bluetooth.BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE;
		String stateExtra = android.bluetooth.BluetoothAdapter.EXTRA_SCAN_MODE;
		int state = intent.getIntExtra(stateExtra, - 1);
		int previousState = intent.getIntExtra(prevStateExtra, - 1);
	  }
	}, new android.content.IntentFilter(android.bluetooth.BluetoothAdapter.ACTION_SCAN_MODE_CHANGED));

	String aDiscoverable = android.bluetooth.BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE;
	startActivityForResult(new android.content.Intent(aDiscoverable), blueToothDiscoverableCode);
  }
}





















































