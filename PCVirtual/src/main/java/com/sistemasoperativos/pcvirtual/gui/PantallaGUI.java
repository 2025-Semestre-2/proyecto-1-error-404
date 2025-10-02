package com.sistemasoperativos.pcvirtual.gui;

import com.sistemasoperativos.pcvirtual.controlador.Controlador;
import com.sistemasoperativos.pcvirtual.procesos.BCP;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PantallaGUI extends Application {

    private final Controlador controlador = new Controlador();

    private static TextArea pantallaSalida;
    private static TextField pantallaEntrada;

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private TableView<MemoriaFila> tablaMemoria;
    private TableView<MemoriaFila> tablaDisco;
    private TableView<MemoriaFila> tablaRegistros;
    private ListView<String> listaBPC;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Proyecto 1 de SO");

        // ------------------- Barra superior -------------------
        HBox barraAcciones = new HBox(15);
        barraAcciones.setPadding(new Insets(10));
        barraAcciones.setAlignment(Pos.CENTER);

        Button btnCrearPC      = new Button("Crear PC");
        Button btnEjecutar     = new Button("Ejecutar Automáticamente");
        Button btnPaso         = new Button("Paso a paso");
        Button btnLimpiar      = new Button("Limpiar");
        Button btnEstadisticas = new Button("Estadísticas");

        barraAcciones.getChildren().addAll(btnCrearPC, btnEjecutar, btnPaso, btnLimpiar, btnEstadisticas);

        // ------------------- Panel izquierdo: Procesos / Registros -------------------
        VBox panelIzq = new VBox(10);
        panelIzq.setPadding(new Insets(10));
        panelIzq.setPrefWidth(200);

        Button btnCargar = new Button("Cargar archivo");

        tablaRegistros = new TableView<>();
        tablaRegistros.setPlaceholder(new Label("Registros CPU"));

        TableColumn<MemoriaFila, String> colRegNombre = new TableColumn<>("Registro");
        colRegNombre.setCellValueFactory(data -> data.getValue().llaveProperty());

        TableColumn<MemoriaFila, String> colRegValor = new TableColumn<>("Valor");
        colRegValor.setCellValueFactory(data -> data.getValue().valorProperty());

        tablaRegistros.getColumns().addAll(colRegNombre, colRegValor);
        VBox.setVgrow(tablaRegistros, Priority.ALWAYS);

        panelIzq.getChildren().addAll(btnCargar, tablaRegistros);

        // ------------------- Panel central: BPC actual -------------------
        VBox panelBPC = new VBox(10);
        panelBPC.setPadding(new Insets(10));
        panelBPC.setPrefWidth(250);

        Label lblBPC = new Label("BCP actual CPU1");

        listaBPC = new ListView<>();
        listaBPC.setPrefHeight(200);
        listaBPC.setPlaceholder(new Label("Sin BCP activo"));

        panelBPC.getChildren().addAll(lblBPC, listaBPC);

        // ------------------- Panel derecho: Memoria y Disco -------------------
        VBox panelMemoria = new VBox(5);
        panelMemoria.setPadding(new Insets(10));
        Label lblMemoria = new Label("Memoria");
        tablaMemoria = crearTablaMemoriaDisco();
        panelMemoria.getChildren().addAll(lblMemoria, tablaMemoria);

        VBox panelDisco = new VBox(5);
        panelDisco.setPadding(new Insets(10));
        Label lblDisco = new Label("Disco");
        tablaDisco = crearTablaMemoriaDisco();
        panelDisco.getChildren().addAll(lblDisco, tablaDisco);

        HBox panelTablas = new HBox(20, panelMemoria, panelDisco);

        // ------------------- Panel inferior: Pantalla -------------------
        Label lblPantalla = new Label("Pantalla");
        pantallaEntrada = new TextField();
        pantallaEntrada.setPromptText(">> Ingresar valor:");
        pantallaSalida = new TextArea();
        pantallaSalida.setEditable(false);

        VBox panelPantalla = new VBox(5, lblPantalla, pantallaEntrada, pantallaSalida);
        panelPantalla.setPadding(new Insets(10));
        panelPantalla.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        panelPantalla.setPrefHeight(150);

        // ------------------- Layout principal -------------------
        BorderPane root = new BorderPane();
        root.setTop(barraAcciones);
        root.setLeft(panelIzq);
        root.setCenter(panelBPC);
        root.setRight(panelTablas);
        root.setBottom(panelPantalla);

        Scene scene = new Scene(root, 950, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        // ------------------- Acciones -------------------

        btnCrearPC.setOnAction(e -> {
            CrearPCDialog dlg = new CrearPCDialog(controlador, primaryStage);
            Optional<ConfigPC> res = dlg.showAndWait();
            res.ifPresent(cfg -> {
                if(cfg.getRamMB() > 0 && cfg.getAlmacenamientoMB() > 0) {
                    controlador.CrearPC(cfg.getRamMB(), cfg.getAlmacenamientoMB());
                    System.out.println("PC creada → RAM: " + cfg.getRamMB() + " MB, Almacenamiento: " + cfg.getAlmacenamientoMB() + " MB");
                    actualizarTablas();
                } else {
                    System.out.println("Error: valores inválidos para la PC");
                }
            });
        });

        btnLimpiar.setOnAction(e -> {
            pantallaSalida.clear();
            limpiarTablas();
        });

        btnPaso.setOnAction(e -> {
            try {
                scheduler.shutdownNow();
                controlador.EjecutarInstruccion();
                actualizarTablas();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Error al ejecutar instrucciones: " + ex.getMessage());
            }
        });

        btnEjecutar.setOnAction(e -> {
            Runnable tarea = () -> {
                try {
                    controlador.EjecutarInstruccion();
                    actualizarTablas();
                } catch (Exception ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            };
            scheduler.scheduleAtFixedRate(tarea, 0, 1, TimeUnit.SECONDS);
        });

        btnCargar.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar archivo de programa");
            File archivo = fc.showOpenDialog(primaryStage);
            if(archivo != null){
                try{
                    controlador.CargarPrograma(archivo);
                    System.out.println("Programa cargado: " + archivo.getName());
                    actualizarDisco();
                }catch(Exception ex){
                    System.out.println("Error al cargar programa: " + ex.getMessage());
                }
            }
        });
    }

    // ------------------- Crear tabla de memoria/disco -------------------
    private TableView<MemoriaFila> crearTablaMemoriaDisco(){
        TableView<MemoriaFila> tabla = new TableView<>();
        tabla.setPlaceholder(new Label("Sin datos"));

        TableColumn<MemoriaFila, String> colLlave = new TableColumn<>("Dirección");
        colLlave.setCellValueFactory(data -> data.getValue().llaveProperty());

        TableColumn<MemoriaFila, String> colValor = new TableColumn<>("Valor");
        colValor.setCellValueFactory(data -> data.getValue().valorProperty());

        tabla.getColumns().addAll(colLlave, colValor);
        return tabla;
    }

    // ------------------- Actualizar todas las tablas -------------------
    private void actualizarTablas(){
        actualizarMemoria();
        actualizarDisco();
        actualizarRegistros();
        ActualizarBCP();
    }

    private void actualizarMemoria(){
        Map<String, String> memoria = controlador.TraerMemoriaActual();
        ObservableList<MemoriaFila> datos = FXCollections.observableArrayList();
        memoria.forEach((k,v) -> datos.add(new MemoriaFila(k,v)));
        tablaMemoria.setItems(datos);
    }

    private void actualizarDisco(){
        Map<String, String> disco = controlador.TraerAlmacenamiento();
        ObservableList<MemoriaFila> datos = FXCollections.observableArrayList();
        disco.forEach((k,v) -> datos.add(new MemoriaFila(k,v)));
        tablaDisco.setItems(datos);
    }

    private void actualizarRegistros(){
        try{
            Map<String,String> registros = controlador.TraerRegistros();
            ObservableList<MemoriaFila> datos = FXCollections.observableArrayList();
            registros.forEach((k,v) -> datos.add(new MemoriaFila(k,v)));
            tablaRegistros.setItems(datos);
        }catch(Exception ex){
            System.out.println("Error al traer registros: " + ex.getMessage());
        }
    }

    // ------------------- Actualizar BCP actual -------------------
    private void ActualizarBCP(){
        BCP bcp = controlador.TraerBCPActual();
        if(bcp == null){
            listaBPC.getItems().clear();
            listaBPC.getItems().add("No hay BCP activo");
            return;
        }

        ObservableList<String> datos = FXCollections.observableArrayList();
        datos.add("ID: " + bcp.getID());
        datos.add("Nombre: " + bcp.getNombre());
        datos.add("Estado: " + bcp.getEstado());
        datos.add("CPU asignado: " + bcp.getCpuAsignado());
        datos.add("Tiempo ejecutado: " + bcp.getTiempoEjecutado() + " ms");

        // Agregamos registros principales
        Map<String, String> regs = bcp.getRegistros();
        datos.add("Registros:");
        regs.forEach((k,v) -> datos.add("  " + k + " = " + v));

        listaBPC.setItems(datos);
    }

    // ------------------- Limpiar tablas -------------------
    private void limpiarTablas(){
        tablaMemoria.getItems().clear();
        tablaDisco.getItems().clear();
        tablaRegistros.getItems().clear();
        listaBPC.getItems().clear();
    }

    // ------------------- Clase para filas -------------------
    public static class MemoriaFila{
        private final SimpleStringProperty llave;
        private final SimpleStringProperty valor;

        public MemoriaFila(String llave, String valor){
            this.llave = new SimpleStringProperty(llave);
            this.valor = new SimpleStringProperty(valor);
        }

        public String getLlave(){ return llave.get(); }
        public String getValor(){ return valor.get(); }

        public void setLlave(String llave){ this.llave.set(llave); }
        public void setValor(String valor){ this.valor.set(valor); }

        public SimpleStringProperty llaveProperty(){ return llave; }
        public SimpleStringProperty valorProperty(){ return valor; }
    }

    // ------------------- Métodos de pantalla -------------------
    public static void escribir(String texto){
        pantallaSalida.appendText(texto + "\n");
    }

    public static String leer(){
        String texto = pantallaEntrada.getText();
        pantallaEntrada.clear();
        return texto;
    }

    public static void main(String[] args){
        launch(args);
    }
}
