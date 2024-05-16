package com.example.oop_project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MasinaApp extends Application {
    private Kohviautomaat kohviautomaat;
    private Snäkiautomaat snäkiautomaat;
    private double raha = 0.0;
    private Label saldo;
    private Button ostaKohviNupp;
    private TextField kohviSisend;
    private Button ostaSnäkiNupp;
    private TextField snäkiSisend;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Kohvi- ja Snäkimasin");

        // Masinate initsialiseerimine ja toodete lisamine
        try {
            lisaTooted();
        } catch (FileNotFoundException e) {
            näitaHoiatust("Faili viga", "Toodete faili ei leitud.");
        }

        // Kasutajaliidese elementide loomine
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        saldo = new Label("Saldo: 0.00€");
        TextField rahaSisend = new TextField();
        rahaSisend.setPromptText("Sisestage, kui palju teil raha on");
        Button lisaRahaNupp = new Button("Lisa Raha");

        // Vahekaardipaneel kohvi- ja snäkimasinatele
        TabPane tabPane = new TabPane();
        Tab kohviTab = new Tab("Kohviautomaat", looKohviPaneel());
        Tab snäkiTab = new Tab("Snäkiautomaat", looSnäkiPaneel());
        tabPane.getTabs().addAll(kohviTab, snäkiTab);

        // Disable buy buttons and input fields initially
        ostaKohviNupp.setDisable(true);
        kohviSisend.setDisable(true);
        ostaSnäkiNupp.setDisable(true);
        snäkiSisend.setDisable(true);

        // Raha lisamise nupu tegevus
        lisaRahaNupp.setOnAction(e -> {
            try {
                raha += Double.parseDouble(rahaSisend.getText());
                saldo.setText("Saldo: " + String.format("%.2f", raha) + "€");
                rahaSisend.clear();

                // Enable buy buttons and input fields
                ostaKohviNupp.setDisable(false);
                kohviSisend.setDisable(false);
                ostaSnäkiNupp.setDisable(false);
                snäkiSisend.setDisable(false);
            } catch (NumberFormatException ex) {
                näitaHoiatust("Vale sisend", "Palun sisestage korrektne rahasumma.");
            }
        });

        // Paigutuse seadistamine
        root.getChildren().addAll(saldo, rahaSisend, lisaRahaNupp, tabPane);

        // Lava näitamine
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void lisaTooted() throws IOException {
        // Snäkiautomaadi initsialiseerimine ja toodete lisamine
        snäkiautomaat = new Snäkiautomaat();
        loeTootedFailist("tooted.txt");

        // Kohviautomaadi initsialiseerimine ja kohvide lisamine
        kohviautomaat = new Kohviautomaat(0.5, 1);
        loeKohvFailist("kohv.txt");
    }

    private void loeTootedFailist(String failinimi) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(failinimi))) {
            String rida;
            while ((rida = br.readLine()) != null) {
                String[] osad = rida.split(",");
                String nimi = osad[0];
                double hind = Double.parseDouble(osad[1]);
                int kogus = Integer.parseInt(osad[2]);
                snäkiautomaat.lisaToode(new Toode(nimi, hind, kogus));
            }
        }
    }

    private void loeKohvFailist(String failinimi) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(failinimi))) {
            String rida;
            while ((rida = br.readLine()) != null) {
                String[] osad = rida.split(",");
                String nimi = osad[0];
                double piimaKogus = Double.parseDouble(osad[1]);
                double kohviSeguKogus = Double.parseDouble(osad[2]);
                double hind = Double.parseDouble(osad[3]);
                kohviautomaat.lisaKohv(new Kohv(nimi, piimaKogus, kohviSeguKogus, hind));
            }
        }
    }

    private VBox looKohviPaneel() {
        VBox kohviPaneel = new VBox(10);
        ListView<String> kohviListView = new ListView<>();
        uuendaKohviList(kohviListView);
        ostaKohviNupp = new Button("Osta Kohvi");
        kohviSisend = new TextField();
        kohviSisend.setPromptText("Sisesta valiku number");

        ostaKohviNupp.setOnAction(e -> {
            try {
                int kohviIndex = Integer.parseInt(kohviSisend.getText()) - 1;
                raha = kohviautomaat.sooritaOst(kohviIndex + 1, raha);
                saldo.setText("Saldo: " + String.format("%.2f", raha) + "€");
                uuendaKohviList(kohviListView);
                kohviSisend.clear();
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                näitaHoiatust("Vale sisend", "Palun sisestage korrektne valiku number.");
            }
        });

        kohviPaneel.getChildren().addAll(new Label("Kohvid"), kohviListView, kohviSisend, ostaKohviNupp);
        return kohviPaneel;
    }

    private VBox looSnäkiPaneel() {
        VBox snäkiPaneel = new VBox(10);
        ListView<String> snäkiListView = new ListView<>();
        uuendaSnäkiList(snäkiListView);
        ostaSnäkiNupp = new Button("Osta Snäkki");
        snäkiSisend = new TextField();
        snäkiSisend.setPromptText("Sisesta toote number");

        ostaSnäkiNupp.setOnAction(e -> {
            try {
                int snäkiIndex = Integer.parseInt(snäkiSisend.getText()) - 1;
                raha = snäkiautomaat.sooritaOst(snäkiIndex + 1, raha);
                saldo.setText("Saldo: " + String.format("%.2f", raha) + "€");
                uuendaSnäkiList(snäkiListView);
                snäkiSisend.clear();
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                näitaHoiatust("Vale sisend", "Palun sisestage korrektne toote number.");
            }
        });

        snäkiPaneel.getChildren().addAll(new Label("Snäkid"), snäkiListView, snäkiSisend, ostaSnäkiNupp);
        return snäkiPaneel;
    }

    private void uuendaKohviList(ListView<String> kohviListView) {
        kohviListView.getItems().clear();
        int number = 1;
        for (Kohv kohv : kohviautomaat.getPakutavadKohvid()) {
            String item = number + ". " + kohv.getKohviNimi() + " - " + kohv.getKohviHind() + "€";
            if (kohv.getPiimaKogusL() > kohviautomaat.getPiimaKogusLiitrites()) {
                item += " (Pole piisavalt piima)";
            }
            if (kohv.getKohviSeguKogusL() > kohviautomaat.getKohviSeguKogusLiitrites()) {
                item += " (Pole piisavalt segu)";
            }
            kohviListView.getItems().add(item);
            number++;
        }
    }

    private void uuendaSnäkiList(ListView<String> snäkiListView) {
        snäkiListView.getItems().clear();
        int number = 1;
        for (Toode toode : snäkiautomaat.getTooted()) {
            snäkiListView.getItems().add(number + ". " + toode.getNimi() + " - " + toode.getHind() + "€ (" + toode.getKogus() + " tk)");
            number++;
        }
    }

    private void näitaHoiatust(String pealkiri, String sisu) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(pealkiri);
        alert.setHeaderText(null);
        alert.setContentText(sisu);
        alert.showAndWait();
    }
}
