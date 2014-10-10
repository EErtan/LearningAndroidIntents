package com.nullcognition.learningandroidintents;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ActivityFromIntent extends Activity {

  private static final int requestCode = 2;
  android.widget.ImageView imageView;

  @Override
  protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_fromintent);

	receiveIntent();

	imageView = (android.widget.ImageView)findViewById(com.nullcognition.learningandroidintents.R.id.imageView);

	android.widget.Button button = (android.widget.Button)findViewById(com.nullcognition.learningandroidintents.R.id.button);
	button.setOnClickListener(new android.view.View.OnClickListener() {

	  @Override
	  public void onClick(android.view.View inView){
		android.content.Intent intent = new android.content.Intent();
		intent.setType("image/*");
		intent.setAction(android.content.Intent.ACTION_GET_CONTENT);
		intent.addCategory(android.content.Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, requestCode);
	  }
	});
  }

  private void receiveIntent(){
	// register to receive the intent in the manifest
	android.content.Intent intent = getIntent(); // action and type extraction from intent
	String action = intent.getAction();
	String type = intent.getType();

	if(android.content.Intent.ACTION_SEND.equals(action) && type != null){
	  if("text/html".equals(type)){
		String text = intent.getStringExtra(android.content.Intent.EXTRA_TEXT);
		if(text != null){
		  android.widget.Toast.makeText(this, text, android.widget.Toast.LENGTH_SHORT).show();
		}
	  }
	}
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.my, menu);
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
	  setResult(RESULT_OK, new android.content.Intent());
	  finish();
	  // setResult(RESULT_CANCELED, new android.content.Intent());
	  // finish();
	  return true;
	}

	return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onActivityResult(int inRequestCode, int resultCode, android.content.Intent data){
	super.onActivityResult(inRequestCode, resultCode, data);
	java.io.InputStream stream = null;
	android.graphics.Bitmap bitmap = null;

	if(inRequestCode == requestCode && resultCode == android.app.Activity.RESULT_OK){
	  if(bitmap != null){bitmap.recycle();}

	  try{
		stream = getContentResolver().openInputStream(data.getData());
		bitmap = android.graphics.BitmapFactory.decodeStream(stream);
		imageView.setImageBitmap(bitmap);
	  }
	  catch(java.io.FileNotFoundException e){e.printStackTrace();}
	  finally{
		if(stream != null){
		  try{stream.close();}
		  catch(java.io.IOException e){e.printStackTrace();}
		}
	  }
	}
  }
}
