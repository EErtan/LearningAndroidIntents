package com.nullcognition.learningandroidintents;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ActivityMain extends Activity {

  public final int blueToothDiscoverableCode = 1001;
  int blueToothRequestCode = 101;
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

	android.widget.Button button2 = (android.widget.Button)findViewById(com.nullcognition.learningandroidintents.R.id.button2);
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

	initBluetooth();

	blueToothDiscoverable();
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





















































