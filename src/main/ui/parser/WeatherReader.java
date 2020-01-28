package ui.parser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class WeatherReader {
    private String apiKey = "08d326c08698983b570a9eda65ae078a";
    private String api = "http://api.openweathermap.org/data/2.5/weather?q=Vancouver&APPID=";
    private String city;
    String theUrl = api + apiKey;


    public WeatherReader() {
        city = "Vancouver";
    }

    public String getWeather() throws IOException {
        BufferedReader br = null;
        try {
            URL url = new URL(theUrl);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            JSONObject jsonData = new JSONObject(sb.toString());
            Parser parser = new Parser();
            return parser.readWeather(jsonData,city);
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

}
