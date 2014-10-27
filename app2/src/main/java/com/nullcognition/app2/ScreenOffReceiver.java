package com.nullcognition.app2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenOffReceiver extends BroadcastReceiver {

  public ScreenOffReceiver(){}

  @Override
  public void onReceive(Context inContext, Intent intent){

	final Context context = inContext;
	android.media.AudioManager audioManager = (android.media.AudioManager)context.getSystemService(android.content.Context.AUDIO_SERVICE);

	if((intent.getAction() == android.content.Intent.ACTION_SCREEN_ON) && (audioManager.getMode() == android.media.AudioManager.MODE_IN_CALL)){

	  audioManager.setSpeakerphoneOn(true);
	}
	else if((intent.getAction() == android.content.Intent.ACTION_SCREEN_OFF) && (audioManager.getMode() == android.media.AudioManager.MODE_IN_CALL)){

	  audioManager.setSpeakerphoneOn(false);
	}

  }

}
