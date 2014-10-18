package com.nullcognition.learningandroidintents;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ActivityMain extends Activity {

  public final int blueToothDiscoverableCode = 1001;
  int blueToothRequestCode = 101;
  int speechRequestCode = 123;
  com.nullcognition.learningandroidintents.BluetoothStateReceiver btsr;

  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
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
		i.putExtra(android.speech.RecognizerIntent.EXTRA_PROMPT, "Speaking");
		i.putExtra(android.speech.RecognizerIntent.EXTRA_MAX_RESULTS, 1);
		i.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, java.util.Locale.ENGLISH);
		startActivityForResult(i, speechRequestCode); // active internet connection is required
	  }
	});

	checkForTTS();

	initBluetooth();

	blueToothDiscoverable();

	proximityAlert();
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

  public class ProximityAlertReceiver extends android.content.BroadcastReceiver {

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





















































