package ro.pub.cs.systems.pdsd.practicaltest02var01;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class PracticalTest02Var01MainActivity extends Activity {

	// Server widgets
		private EditText     serverPortEditText       = null;
		private Button       connectButton            = null;
		
		// Client widgets
		private EditText     clientAddressEditText    = null;
		private EditText     clientPortEditText       = null;
		private Button       tempButton = null;
		private Button       humidityButton = null;
		private Button       allInfoButton = null;
		private TextView     weatherForecastTextView  = null;
		
		private ServerThread serverThread             = null;
		private ClientThread clientThread             = null;
		
		private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();
		private class ConnectButtonClickListener implements Button.OnClickListener {
			
			@Override
			public void onClick(View view) {
				String serverPort = serverPortEditText.getText().toString();
				if (serverPort == null || serverPort.isEmpty()) {
					Toast.makeText(
						getApplicationContext(),
						"Server port should be filled!",
						Toast.LENGTH_SHORT
					).show();
					return;
				}
				
				serverThread = new ServerThread(Integer.parseInt(serverPort));
				if (serverThread.getServerSocket() != null) {
					serverThread.start();
				} else {
					Log.e("TAG", "[MAIN ACTIVITY] Could not creat server thread!");
				}
				
			}
		}
		

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_practical_test02_var01_main);
			
			serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
			connectButton = (Button)findViewById(R.id.connect_button);
			connectButton.setOnClickListener(connectButtonClickListener);
			
			clientAddressEditText = (EditText)findViewById(R.id.client_address_edit_text);
			clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
		
			
			
			tempButton = (Button)findViewById(R.id.temp_button);
			tempButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String clientAddress = clientAddressEditText.getText().toString();
					String clientPort    = clientPortEditText.getText().toString();
					if (clientAddress == null || clientAddress.isEmpty() ||
						clientPort == null || clientPort.isEmpty()) {
						Toast.makeText(
							getApplicationContext(),
							"Client connection parameters should be filled!",
							Toast.LENGTH_SHORT
						).show();
						return;
					}
					
					if (serverThread == null || !serverThread.isAlive()) {
						Log.e("TAG", "[MAIN ACTIVITY] There is no server to connect to!");
						return;
					}
					
		
					String informationType = "Temperature";
					
					weatherForecastTextView.setText(" ");
					
					clientThread = new ClientThread(
							clientAddress,
							Integer.parseInt(clientPort),
							informationType,
							weatherForecastTextView);
					clientThread.start();
				}
					});
			
			
			allInfoButton = (Button)findViewById(R.id.all_info);
			allInfoButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String clientAddress = clientAddressEditText.getText().toString();
					String clientPort    = clientPortEditText.getText().toString();
					if (clientAddress == null || clientAddress.isEmpty() ||
						clientPort == null || clientPort.isEmpty()) {
						Toast.makeText(
							getApplicationContext(),
							"Client connection parameters should be filled!",
							Toast.LENGTH_SHORT
						).show();
						return;
					}
					
					if (serverThread == null || !serverThread.isAlive()) {
						Log.e("TAG", "[MAIN ACTIVITY] There is no server to connect to!");
						return;
					}
					
		
					String informationType = "all";
					
					weatherForecastTextView.setText(" ");
					
					clientThread = new ClientThread(
							clientAddress,
							Integer.parseInt(clientPort),
							informationType,
							weatherForecastTextView);
					clientThread.start();
				}
					});
			
			humidityButton = (Button)findViewById(R.id.humidity_button);
			humidityButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String clientAddress = clientAddressEditText.getText().toString();
					String clientPort    = clientPortEditText.getText().toString();
					if (clientAddress == null || clientAddress.isEmpty() ||
						clientPort == null || clientPort.isEmpty()) {
						Toast.makeText(
							getApplicationContext(),
							"Client connection parameters should be filled!",
							Toast.LENGTH_SHORT
						).show();
						return;
					}
					
					if (serverThread == null || !serverThread.isAlive()) {
						Log.e("TAG", "[MAIN ACTIVITY] There is no server to connect to!");
						return;
					}
					
		
					String informationType = "Humidity";
					
					weatherForecastTextView.setText(" ");
					
					clientThread = new ClientThread(
							clientAddress,
							Integer.parseInt(clientPort),
							informationType,
							weatherForecastTextView);
					clientThread.start();
				}
					});
					
			weatherForecastTextView = (TextView)findViewById(R.id.weather_forecast_text_view);
		}
		
		@Override
		protected void onDestroy() {
			if (serverThread != null) {
				serverThread.stopThread();
			}
			super.onDestroy();
		}
}
