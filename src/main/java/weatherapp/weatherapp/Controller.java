package weatherapp.weatherapp;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;



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
    String cityName;
    JsonNode jsonNode;
    String API_ID = "5e06438d50f63775dfac81c821f1f979";
    String IMAGE;


    public void getCityName() {

        //check if enter pressed
        cityNameTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                cityName = cityNameTextField.getText().strip();
            }
        });


        if(cityName != null){
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&&appid=" + API_ID+"&units=metric");
                System.out.println(url.toExternalForm());

//                StringBuilder result = new StringBuilder();
//                URLConnection connection = url.openConnection();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    result.append(line);
//
//                }
//                bufferedReader.close();
//                System.out.println(a);

                //Get request from api: longitude / latitude
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

                ObjectMapper mapper = new ObjectMapper();
                jsonNode = mapper.readTree(result2);

                System.out.println(jsonNode.toPrettyString());

            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            getWeatherInformation();
        }
    }
    public void getWeatherInformation(){
        getCountry();
        getTemperature();
        getWeather();
    }
    private void getCountry(){
        String temp = String.valueOf(jsonNode.get("name")
                + ", " + jsonNode.get("sys").get("country")).replaceAll("\"","");
        countryNameLabel.setText(temp);
    }
    private void getTemperature(){
        JsonNode js = jsonNode.get("main");

        String temp = String.valueOf(js.get("temp")+" °C").replaceAll("\"","");
        temperatureLabel.setText(temp);
        temp = String.valueOf(js.get("feels_like")+" °C").replaceAll("\"","");
        feelsLikeLabel.setText("Feels: "+temp);
        temp = String.valueOf(js.get("temp_min")+" °C").replaceAll("\"","");
        minimumLabel.setText("Minimum: "+ temp);
        temp = String.valueOf(js.get("temp_max")+" °C").replaceAll("\"","");
        maximumLabel.setText("Maximum: "+temp);
        temp = String.valueOf(js.get("pressure")+" hPa").replaceAll("\"","");
        pressureLabel.setText("Pressure: "+temp);
        temp = String.valueOf(js.get("humidity")+" %").replaceAll("\"","");
        humidityLabel.setText("Humidity: "+temp);
    }
    private void getWeather(){
        String temp = String.valueOf(jsonNode.get("clouds").get("all") + " %").replaceAll("\"","");
        cloudsLabel.setText("Clouds: " +temp);
        JsonNode js = jsonNode.get("weather");
        temp = String.valueOf(js.get(0).get("description")).replaceAll("\"","");
        temp = temp.substring(0,1).toUpperCase() + temp.substring(1);
        cloudyLabel.setText(temp);
        IMAGE = String.valueOf(js.get(0).get("icon"));

    }

}

