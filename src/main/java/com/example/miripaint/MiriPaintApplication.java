package com.example.miripaint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MiriPaintApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            MiriPaintApplication.class.getResource("miripaint-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 900);
        scene.setFill(Color.WHITESMOKE);
        stage.setTitle("MiriPaint");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}