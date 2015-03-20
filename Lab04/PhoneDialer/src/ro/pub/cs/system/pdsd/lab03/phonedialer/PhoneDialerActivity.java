package ro.pub.cs.system.pdsd.lab03.phonedialer;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhoneDialerActivity extends Activity {
	
	final private int[] buttonIds = {
		R.id.button_0,
		R.id.button_1,
		R.id.button_2,
		R.id.button_3,
		R.id.button_4,
		R.id.button_5,
		R.id.button_6,
		R.id.button_7,
		R.id.button_8,
		R.id.button_9,
		R.id.button_star,
		R.id.button_hashtag
	};

	final private int[] imageButtonIds = {
			R.id.button_call,
			R.id.button_back,
			R.id.button_hangup,
			R.id.button_contact
		};
	final private int contact_id = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_dialer);
		for (int k=0; k<buttonIds.length; k++) {
			Button button = (Button)findViewById(buttonIds[k]);
			button.setOnClickListener(keypad);
		}
		for (int k=0; k<imageButtonIds.length; k++) {
			ImageButton button = (ImageButton)findViewById(imageButtonIds[k]);
			button.setOnClickListener(keypad);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_dialer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private KeyPadButton keypad = new KeyPadButton();
	
	private class KeyPadButton implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			EditText edit_text = (EditText) findViewById(R.id.edit_text); 
			String phone_number = edit_text.getText().toString();
			
			if(v instanceof Button){
				phone_number = phone_number + ((Button) v).getText().toString();
				edit_text.setText(phone_number);
			}
			
			if(v instanceof ImageButton){
				switch (v.getId()){
				case R.id.button_call:
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:"+phone_number));
					startActivity(intent);
					break;
				case R.id.button_back:
					if(phone_number.length() > 0)
					phone_number = phone_number.substring(0, phone_number.length() - 1);
					edit_text.setText(phone_number);
					break;
				case R.id.button_hangup: finish(); break;
				case R.id.button_contact: 
					Intent i = new Intent("ro.pub.cs.systems.pdsd.intent.action.CONTACT_MANAGER");
					i.putExtra("phone", phone_number);
					startActivityForResult(i, contact_id) ;
				
					
					break;
				}
			}
			
		}
		
	}
	
	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent intent){
		switch(reqCode){
		case contact_id:
			Toast.makeText(this, "ActivityReturned"+resultCode, Toast.LENGTH_SHORT).show();break;
		}
		
	}
}
