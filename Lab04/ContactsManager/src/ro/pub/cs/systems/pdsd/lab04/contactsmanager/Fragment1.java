package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment1 extends Fragment {
	@Override
	  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle state) {
	    return layoutInflater.inflate(R.layout.fragment1, container, false);
	}
	
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
			case R.id.button_save: break;
			case R.id.button_cancel: break;
			}
		}
	
	}
}
