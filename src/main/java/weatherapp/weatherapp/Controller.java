package weatherapp.weatherapp;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.*;


public class Controller {
    @FXML
    TextField cityNameTextField;
    @FXML
    Label countryNameLabel,
            factInfoLabel,
            temperatureLabel,
            cloudyLabel,
            feelsLikeLabel,
            minimumLabel,
            maximumLabel,
            pressureLabel,
            humidityLabel,
            cloudsLabel;
    String latitude, longitude, country, cityName;
    String API_ID = "5e06438d50f63775dfac81c821f1f979";


    public void getCityName() throws IOException {

        cityNameTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                cityName = cityNameTextField.getText().strip();
            }
        });
        if(cityName != null){
            try {
                StringBuilder result = new StringBuilder();
                URL url = new URL("http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=1&appid=" + API_ID);
                System.out.println(url.toExternalForm());

//                URLConnection connection = url.openConnection();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    result.append(line);
//
//                }
//                bufferedReader.close();
//                System.out.println(a);

                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String result2 = response.toString();
                System.out.println("a "+ result2);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(result2);

                System.out.println(jsonNode.toPrettyString());
                System.out.println(jsonNode.get(0).get("name"));

            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

