package ro.pub.cs.systems.pdsd.practicaltest02var01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;



import android.util.Log;
import android.widget.Toast;

public class CommunicationThread extends Thread {
	
	private ServerThread serverThread;
	private Socket       socket;
	
	public CommunicationThread(ServerThread serverThread, Socket socket) {
		this.serverThread = serverThread;
		this.socket       = socket;
	}
	
	@Override
	public void run() {
		if (socket != null) {
			try {
				BufferedReader bufferedReader = Utilities.getReader(socket);
				PrintWriter    printWriter    = Utilities.getWriter(socket);
				if (bufferedReader != null && printWriter != null) {
					Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");
					String informationType = bufferedReader.readLine();
					ArrayList<WeatherForecastInformation> data = serverThread.getData();
					WeatherForecastInformation weatherForecastInformation = null;
	
							HttpClient httpClient = new DefaultHttpClient();
							String url=Constants.WEB_SERVICE_ADDRESS ;
							HttpGet httpGet=new HttpGet(url);
							ResponseHandler<String> responseHandler = new BasicResponseHandler();
							
							String response = httpClient.execute(httpGet,responseHandler);
							
							Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
							
							if (response != null) {
								Log.i(Constants.TAG, "Response not null");
										JSONObject content = new JSONObject(response);
										
										JSONObject main = content.getJSONObject("main");
										
										String temperature = main.getString("temp");
										String humidity = main.getString("humidity");
										
										weatherForecastInformation = new WeatherForecastInformation(
												temperature,
												humidity);

										serverThread.setData(weatherForecastInformation);
										System.out.println("TEMP" + temperature);
										
								}
						if (weatherForecastInformation != null) {
							String result = null;
							if ("all".equals(informationType)) {
								result = weatherForecastInformation.toString();
							} else if ("Temperature".equals(informationType)) {
								result = weatherForecastInformation.getTemperature();
							} else if ("Humidity".equals(informationType)) {
								result = weatherForecastInformation.getHumidity();
							} else {
								result = "Wrong information type (all / temperature / humidity)!";
							}
							printWriter.println(result);
							printWriter.flush();
						
						} else {
							Log.e(Constants.TAG, "[COMMUNICATION THREAD] Weather Forecast information is null!");
						}
						
				}
				socket.close();
			} catch (IOException ioException) {
				Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
				if (Constants.DEBUG) {
					ioException.printStackTrace();
				}
			} catch (JSONException jsonException) {
				Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + jsonException.getMessage());
				if (Constants.DEBUG) {
					jsonException.printStackTrace();
				}				
			}
		} else {
			Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
		}
	}

}
