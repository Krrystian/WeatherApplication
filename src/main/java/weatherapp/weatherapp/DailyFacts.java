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
 * Using Rapid API - numbers
 */
public class DailyFacts {
    static String factInformation;

    public static String getFactInformation() {
        return factInformation;
    }

    public static void getDailyFact() throws IOException {
        String[]date = String.valueOf(LocalDate.now()).split("-");

        URL url = new URL("https://numbersapi.p.rapidapi.com/"+date[1]+"/"+date[2]+"/date?fragment=true&json=true");
//        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("X-RapidAPI-Key", "52f6693dacmsh65e981a731143fbp1af2bajsn2013c8ec5ac6");
        conn.addRequestProperty("X-RapidAPI-Host", "numbersapi.p.rapidapi.com");
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

        factInformation = (date[2]+"."+date[1]+"."+jsonNode.get("year") +" "+ (jsonNode.get("text"))).replaceAll("\"","");
//        System.out.println(factInformation);

    }
}
