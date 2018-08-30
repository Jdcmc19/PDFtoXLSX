package sample;

import Classes.Excel;
import Classes.Informacion;
import Classes.PDFManager;
import Classes.Transaccion;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Conversión de Estado de Cuenta");
        primaryStage.setScene(new Scene(root, 431, 183));
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);

    }
}