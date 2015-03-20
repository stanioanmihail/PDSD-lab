package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment1 extends Fragment {
	final public static int start_native_contacts = 2;
	@Override
	  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {
	    return layoutInflater.inflate(R.layout.fragment1, container, false);
	}
	
	private ButtonOnClickListener buttonOnClickListener = new ButtonOnClickListener();
	private class ButtonOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.button_show:
				FragmentManager fragmentManager = getActivity().getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				Fragment2 additionalDetailsFragment = (Fragment2)fragmentManager.findFragmentById(R.id.frame2);
				
				if (additionalDetailsFragment == null) {
				  fragmentTransaction.add(R.id.frame2, new Fragment2());
				  ((Button)v).setText(getActivity().getResources().getString(R.string.hide_additional_fields));
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
				
				} else {
				  fragmentTransaction.remove(additionalDetailsFragment);
				  ((Button)v).setText(getActivity().getResources().getString(R.string.show_additional_fields));
				  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
				}
				fragmentTransaction.commit();
				break;
			case R.id.button_save: 
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				
				EditText et_name = (EditText)getActivity().findViewById(R.id.edit_name);
				String name = et_name.getText().toString();
				
				EditText et_phone = (EditText)getActivity().findViewById(R.id.edit_phone);
				String phone = et_phone.getText().toString();
				
				EditText et_address = (EditText)getActivity().findViewById(R.id.edit_site);
				String site = "";
				if(et_address != null){
					site = et_address.getText().toString();
				}
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
				}
				if(site != null) {
				   intent.putExtra(ContactsContract.Intents.Insert.COMPANY, site);
				}
				
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				getActivity().startActivityForResult(intent, start_native_contacts);
				break;
				
			case R.id.button_cancel:
				getActivity().setResult(Activity.RESULT_CANCELED, new Intent());
				getActivity().finish();
				break;
			}
		}
	
	}
	@Override
	public void onActivityCreated(Bundle saveInstanceState){
		super.onActivityCreated(saveInstanceState);
		
		Button additionalDetailsButton = (Button)getActivity().findViewById(R.id.button_show);
		additionalDetailsButton.setOnClickListener(buttonOnClickListener);
		
		Button saveButton = (Button)getActivity().findViewById(R.id.button_save);
		saveButton.setOnClickListener(buttonOnClickListener);
		
		Button cancelButton = (Button)getActivity().findViewById(R.id.button_cancel);
		cancelButton.setOnClickListener(buttonOnClickListener);
		
		EditText phoneEditText = (EditText)getActivity().findViewById(R.id.edit_phone);
		Intent intent = getActivity().getIntent();
		
		if(intent != null){
			String phone = intent.getStringExtra("phone");
			if(phone != null){
				phoneEditText.setText(phone);
			}else{
				Activity activity = getActivity();
				Toast.makeText(activity, "Insert Phone Number", Toast.LENGTH_LONG).show();
			}
		
		}
	}
		
}

