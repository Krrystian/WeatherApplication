package weatherapp.weatherapp;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;


import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Controller implements Initializable {
    @FXML
    Pane settingsPane;
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
            cloudsLabel,
            cityNameLabel;
    @FXML
    ImageView weatherIcon;
    String cityName;
    JsonNode jsonNode;
    String API_ID = "5e06438d50f63775dfac81c821f1f979";
    String IMAGE;


//    public void getCityName(){
//
//        //check if enter pressed
//        cityNameTextField.setOnKeyPressed(keyEvent -> {
//            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
//                cityName = cityNameTextField.getText().strip();
//            }
//        });
//
//
//        if(cityName != null){
//            try {
//                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&&appid=" + API_ID+"&units=metric");
//                System.out.println(url.toExternalForm());
//
////                StringBuilder result = new StringBuilder();
////                URLConnection connection = url.openConnection();
////                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////                String line;
////                while ((line = bufferedReader.readLine()) != null) {
////                    result.append(line);
////
////                }
////                bufferedReader.close();
////                System.out.println(a);
//
//                //Get request from api: longitude / latitude
//                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//                String result2 = response.toString();
//
//                ObjectMapper mapper = new ObjectMapper();
//                jsonNode = mapper.readTree(result2);
//
//                System.out.println(jsonNode.toPrettyString());
//
//            }catch (IOException e){
//                System.out.println(e.getMessage());
//            }
//            getWeatherInformation();
//        }
//    }

    public void getWeatherInformation(){
        getCountry();
        getTemperature();
        getWeather();
        getImage();
    }
    private void getCountry(){
        String temp = (jsonNode.get("name")
                + ", " + jsonNode.get("sys").get("country")).replaceAll("\"","");
        countryNameLabel.setText(temp);
    }
    private void getTemperature(){
        JsonNode js = jsonNode.get("main");

        String temp = (js.get("temp") + " °C").replaceAll("\"","");
        temperatureLabel.setText(temp);
        temp = (js.get("feels_like") + " °C").replaceAll("\"","");
        feelsLikeLabel.setText("Feels: "+temp);
        temp = (js.get("temp_min") + " °C").replaceAll("\"","");
        minimumLabel.setText("Minimum: "+ temp);
        temp = (js.get("temp_max") + " °C").replaceAll("\"","");
        maximumLabel.setText("Maximum: "+temp);
        temp = (js.get("pressure") + " hPa").replaceAll("\"","");
        pressureLabel.setText("Pressure: "+temp);
        temp = (js.get("humidity") + " %").replaceAll("\"","");
        humidityLabel.setText("Humidity: "+temp);
    }
    private void getWeather(){
        String temp = (jsonNode.get("clouds").get("all") + " %").replaceAll("\"","");
        cloudsLabel.setText("Clouds: " +temp);
        JsonNode js = jsonNode.get("weather");
        temp = String.valueOf(js.get(0).get("description")).replaceAll("\"","");
        temp = temp.substring(0,1).toUpperCase() + temp.substring(1);
        cloudyLabel.setText(temp);
        IMAGE = String.valueOf(js.get(0).get("icon")).replaceAll("\"","");
    }
    private void getImage(){
        String iconUrl = "http://openweathermap.org/img/w/" + IMAGE + ".png";
        //System.out.println(iconUrl);
        Image image = new Image(iconUrl);
        weatherIcon.setImage(image);
    }

    //WE CAN MAKE ONE FUNCTION CALLED in getSettings and basicLocation
    public void getSettings(){
        TextInputDialog dialog = new TextInputDialog(cityName);
        dialog.setTitle("Change city");
        dialog.setHeaderText("Enter new city name.");
        dialog.setContentText("Your new city name: ");
        Optional<String> newCity = dialog.showAndWait();
        if(newCity.isPresent()){
            cityName = newCity.get();
            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&&appid=" + API_ID + "&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

                cityNameLabel.setText(cityName);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
            getWeatherInformation();
        }
    }
    public void basicLocation(String ct) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + ct + "&&appid=" + API_ID + "&units=metric");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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

        cityNameLabel.setText(ct);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            basicLocation(WeatherApp.getCityLocation());
            getWeatherInformation();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

