package com.nullcognition.learningandroidintents;
/**
 * Created by ersin on 13/10/14 at 5:22 PM
 */
public class BluetoothStateReceiver extends android.content.BroadcastReceiver {

  @Override
  public void onReceive(android.content.Context context, android.content.Intent intent){

	String prevStateExtra = android.bluetooth.BluetoothAdapter.EXTRA_PREVIOUS_STATE;
	String stateExtra = android.bluetooth.BluetoothAdapter.EXTRA_STATE;
	int state = intent.getIntExtra(stateExtra, - 1);
	int previousState = intent.getIntExtra(prevStateExtra, - 1);

	String statusText = "";
	if(state == android.bluetooth.BluetoothAdapter.STATE_TURNING_ON){ statusText = "Turing On Bluetooth"; }
	else if(state == android.bluetooth.BluetoothAdapter.STATE_ON){ statusText = "Bluetooth is ON"; }
	else if(state == android.bluetooth.BluetoothAdapter.STATE_TURNING_OFF){ statusText = "Turning Off Bluetooth"; }
	else if(state == android.bluetooth.BluetoothAdapter.STATE_OFF){ statusText = "Bluetooth is OFF"; }

	android.widget.Toast.makeText(context, statusText, android.widget.Toast.LENGTH_SHORT).show();


  }
}
