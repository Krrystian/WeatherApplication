package weatherapp.weatherapp;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CurrentLocation {
    private static final String IPADDRESS_PATTERN =
            "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    private static String ipAdd;


    private static void ipAddress() throws IOException {
        URL url = new URL("http://checkip.dyndns.org/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String ip = br.readLine();
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ip);
        if (matcher.find()){
            ip = matcher.group();
        }
        //System.out.println(ip);
        ipAdd = ip;

    }
    public static String getLocation() throws IOException {
        ipAddress();
        URL url = new URL("http://ip-api.com/json/"+ipAdd+"?fields=17");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        String result = response.toString();
        in.close();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(result);
        return jsonNode.get("city").toString().replaceAll("\"","");

    }
}
