package com.gaproject.jturner;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jefft on 12/28/2016.
 */
public class Main extends Application {

    private Stage window;
    private CSVReader myReader = new CSVReader();
    private GeneticAlgorithm ga = new GeneticAlgorithm();
    private ArrayList<Chromosome> solution = new ArrayList<>();
    private StringProperty str = new SimpleStringProperty("Data Status");

    public static void main(String[] args) {launch(args);}

    //The User interface
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Draft Kings Lineup Bot");

        BorderPane layout = new BorderPane();
        HBox dkuiTop = buildDkuiTop();

        layout.setTop(dkuiTop);

        Scene scene = new Scene(layout, 800, 800);
        window.setScene(scene);
        window.show();
    }

    private HBox buildDkuiTop() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15,12,15,12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #0e5bd6;");

        HBox dkuiTL = buildDkuiTopLeft();
        HBox dkuiTR = buildDkuiTopRight();

        hbox.getChildren().addAll(dkuiTL, dkuiTR);
        return hbox;
    }

    private HBox buildDkuiTopLeft(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15,12,15,12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #0e5bd6;");

        Label playerListStatus = new Label();
        playerListStatus.textProperty().bind(str);
        Button readPlayerList = new Button("Initialize");

        readPlayerList.setOnAction(e-> {
            myReader.clearPlayerPool();
            myReader.createPlayerPool();
            String poolStatus = myReader.getPoolStatus();
            setStr(poolStatus);
        });

        hbox.getChildren().addAll(readPlayerList, playerListStatus);
        return hbox;
    }

    private HBox buildDkuiTopRight(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15,12,15,12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #0e5bd6;");

        Label startRunLabel = new Label("Start Run");
        Button startRunButton = new Button("Start");

        startRunButton.setOnAction(e-> {
            startTheRun();
        });

        hbox.getChildren().addAll(startRunLabel, startRunButton);
        return hbox;
    }

    public final String getStr(){
        return str.get();
    }

    public final void setStr(String str){
        this.str.set(str);
    }

    public StringProperty strProperty(){
        return str;
    }

    private void startTheRun(){
        solution = ga.run();
        System.out.println("..................................");
        System.out.println("Solution: ");
        for (int i = 0; i < 10; i++){
            System.out.println("Solution " + i + " fitness: " + solution.get(i).getFitness());
            solution.get(i).printChromosome();
            solution.get(i).printChromosomeTotals();
        }
        System.out.println("..................................");
    }

}