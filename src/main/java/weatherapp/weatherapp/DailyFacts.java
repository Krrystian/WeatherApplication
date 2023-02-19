package weatherapp.weatherapp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.time.LocalDate;


/**
 * Using Rapid API - NumbersAPI for date facts
 * To make it work you need your own API-key:
 * https://rapidapi.com/divad12/api/numbers-1
 */

public class DailyFacts {
    static String factInformation;

    public static String getFactInformation() {
        return factInformation;
    }

    public static void getDailyFact() throws IOException {
        //Get local time
        String[]date = String.valueOf(LocalDate.now()).split("-");
        
        //Get request
        URL url = new URL("https://numbersapi.p.rapidapi.com/"+date[1]+"/"+date[2]+"/date?fragment=true&json=true");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("X-RapidAPI-Key", "");
        conn.addRequestProperty("X-RapidAPI-Host", "");
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
        JsonNode jsonNode = mapper.readTree(result2);

        //day.month.year "message"
        factInformation = (date[2]+"."+date[1]+"."+jsonNode.get("year") +" "+ (jsonNode.get("text"))).replaceAll("\"","");

    }
}
