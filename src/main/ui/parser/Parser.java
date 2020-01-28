package ui.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Parser {
    private String currentWeather;

    public String readWeather(JSONObject jsonData, String city) throws JSONException {
        JSONArray weatherinfo = jsonData.getJSONArray("weather");
        JSONObject weathertype = weatherinfo.getJSONObject(0);
        JSONObject temperature = jsonData.getJSONObject("main");

        return parseWeather(weathertype, temperature, city);
    }

    private String parseWeather(JSONObject weathertype, JSONObject temperature, String city)
            throws JSONException {
        String weather;
        String type = weathertype.getString("description");
        Double tempInKelvin = temperature.getDouble("temp");

        DecimalFormat f = new DecimalFormat("##.00");
        Double temp = Double.valueOf(f.format(convertToCelsius(tempInKelvin)));
        currentWeather = "Today the temperature in " + city + " is: " + temp.toString() + "°C " + "with " + type;
        return currentWeather;
//        System.out.println(weather);
//        return weather;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    private double convertToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

}
