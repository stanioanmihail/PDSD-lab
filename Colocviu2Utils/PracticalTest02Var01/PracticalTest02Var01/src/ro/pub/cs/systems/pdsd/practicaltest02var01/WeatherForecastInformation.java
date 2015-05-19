package ro.pub.cs.systems.pdsd.practicaltest02var01;



public class WeatherForecastInformation {

	private String temperature;
	private String humidity;

	public WeatherForecastInformation() {
		this.temperature = null;
		this.humidity    = null;
	}

	public WeatherForecastInformation(
			String temperature,
			String humidity) {
		this.temperature = temperature;
		this.humidity    = humidity;
	}
	
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getTemperature() {
		return temperature;
	}
	
	
	
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	
	public String getHumidity() {
		return humidity;
	}
	
	@Override
	public String toString() {
		return Constants.TEMPERATURE + ": " + temperature + " ," + 	     
			   Constants.HUMIDITY + ": " + humidity;
	}

}
