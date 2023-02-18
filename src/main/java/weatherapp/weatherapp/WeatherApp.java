package weatherapp.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WeatherApp extends Application {
    private static String cityLocation;

    public static String getCityLocation() {
        return cityLocation;
    }
    public static void setCityLocation(String cityLocation) {
        WeatherApp.cityLocation = cityLocation;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //Current location
        String city = CurrentLocation.getLocation();
        setCityLocation(city);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WeatherScene.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Weather Application"); //application window name

        //Icon Image
        Image icon = new Image(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("images/cloudy.png")).toExternalForm()));
        stage.getIcons().add(icon);

        //CSS Style
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("SceneStyle.css")).toExternalForm());


        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}