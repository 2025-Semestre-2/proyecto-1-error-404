package com.sistemasoperativos.pcvirtual.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PantallaGUI extends Application {

    private TextArea pantallaSalida;
    private TextField pantallaEntrada;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Proyecto 1 de SO");

        // Botones principales arriba
        HBox botones = new HBox(15);
        botones.setPadding(new Insets(10));
        botones.setAlignment(Pos.CENTER);
        Button btnEjecutar = new Button("Ejecutar");
        Button btnPaso = new Button("Paso a paso");
        Button btnLimpiar = new Button("Limpiar");
        Button btnEstadisticas = new Button("Estadísticas");
        botones.getChildren().addAll(btnEjecutar, btnPaso, btnLimpiar, btnEstadisticas);

        // Panel de cargar archivos + tabla de procesos
        VBox panelIzq = new VBox(10);
        panelIzq.setPadding(new Insets(10));
        panelIzq.setPrefWidth(180);

        Button btnCargar = new Button("Cargar archivos");
        TableView<String> tablaProcesos = new TableView<>();
        tablaProcesos.setPlaceholder(new Label("Procesos / Estados"));

        VBox.setVgrow(tablaProcesos, Priority.ALWAYS);
        panelIzq.getChildren().addAll(btnCargar, tablaProcesos);

        // BPC actual CPU1
        VBox panelBPC = new VBox(10);
        panelBPC.setPadding(new Insets(10));
        panelBPC.setPrefWidth(150);

        Label lblBPC = new Label("BPC actual CPU1");
        ListView<String> listaBPC = new ListView<>();
        listaBPC.getItems().addAll("PC", "IR", "AC", "ETC");

        panelBPC.getChildren().addAll(lblBPC, listaBPC);

        // Tablas memoria y disco
        VBox panelMemoria = new VBox(5);
        panelMemoria.setPadding(new Insets(10));
        Label lblMemoria = new Label("Memoria");
        TableView<String> tablaMemoria = new TableView<>();
        tablaMemoria.setPlaceholder(new Label("Tabla sin columnas"));
        panelMemoria.getChildren().addAll(lblMemoria, tablaMemoria);

        VBox panelDisco = new VBox(5);
        panelDisco.setPadding(new Insets(10));
        Label lblDisco = new Label("Disco");
        TableView<String> tablaDisco = new TableView<>();
        tablaDisco.setPlaceholder(new Label("Tabla sin columnas"));
        panelDisco.getChildren().addAll(lblDisco, tablaDisco);

        HBox panelTablas = new HBox(20, panelMemoria, panelDisco);

        //Pantalla (entrada + salida)
        Label lblPantalla = new Label("Pantalla");
        pantallaEntrada = new TextField();
        pantallaEntrada.setPromptText(">> Ingresar valor:");
        pantallaSalida = new TextArea();
        pantallaSalida.setEditable(false);

        VBox panelPantalla = new VBox(5, lblPantalla, pantallaEntrada, pantallaSalida);
        panelPantalla.setPadding(new Insets(10));
        panelPantalla.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        panelPantalla.setPrefHeight(150);

        //Layout principal
        BorderPane root = new BorderPane();
        root.setTop(botones);
        root.setLeft(panelIzq);
        root.setCenter(panelBPC);
        root.setRight(panelTablas);
        root.setBottom(panelPantalla);

        Scene scene = new Scene(root, 950, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // escribir en la pantalla
    public void escribir(String texto) {
        pantallaSalida.appendText(texto + "\n");
    }

    // leer desde la pantalla
    public String leer() {
        String texto = pantallaEntrada.getText();
        pantallaEntrada.clear();
        return texto;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
