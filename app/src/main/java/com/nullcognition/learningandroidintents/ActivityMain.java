package com.nullcognition.learningandroidintents;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ActivityMain extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
	}

  @Override
  protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data){
	super.onActivityResult(requestCode, resultCode, data);
	if(requestCode == 1 && resultCode == RESULT_OK){
	  android.widget.Toast.makeText(this, "ok", android.widget.Toast.LENGTH_SHORT).show();
	}
	if(resultCode == RESULT_CANCELED){
	  android.widget.Toast.makeText(this, "not ok", android.widget.Toast.LENGTH_SHORT).show();
	}
  }

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
