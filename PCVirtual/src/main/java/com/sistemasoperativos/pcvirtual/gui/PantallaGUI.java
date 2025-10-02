package com.sistemasoperativos.pcvirtual.gui;

import com.sistemasoperativos.pcvirtual.componentes.BUSPantalla;
import com.sistemasoperativos.pcvirtual.controlador.Controlador;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.stream.Collectors;
import java.io.File;
import java.util.Map;

/**
 * Ventana principal del simulador (Proyecto 1 de SO).
 * - La GUI invoca métodos del Controlador y luego refresca la vista con los datos del controlador.
 * - NO se crean archivos/clases adicionales; todo está contenido aquí.
 */
public class PantallaGUI extends Application {

    // ---------- Campos de UI que necesitamos fuera de start() ----------
    private TextArea pantallaSalida;
    private TextField pantallaEntrada;

    private ListView<String> listaBPC;
    private TableView<KV> tablaMemoria;
    private TableView<KV> tablaDisco;

    private final Controlador controlador = new Controlador();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Proyecto 1 de SO");

        // ====== Barra superior ======
        Button btnCrearPC      = new Button("Crear PC");
        Button btnEjecutar     = new Button("Ejecutar");
        Button btnPaso         = new Button("Paso a paso");
        Button btnLimpiar      = new Button("Limpiar");
        Button btnEstadisticas = new Button("Estadísticas");

        HBox barra = new HBox(10, btnCrearPC, btnEjecutar, btnPaso, btnLimpiar, btnEstadisticas);
        barra.setPadding(new Insets(10));
        barra.setAlignment(Pos.CENTER_LEFT);

        // ====== Izquierda: cargar + procesos ======
        Button btnCargar = new Button("Cargar archivos");
        TableView<String> tablaProcesos = new TableView<>();
        tablaProcesos.setPlaceholder(new Label("Procesos / Estados"));
        VBox izq = new VBox(10, btnCargar, tablaProcesos);
        izq.setPadding(new Insets(10));
        izq.setPrefWidth(200);

        // ====== BPC actual ======
        Label lblBPC = new Label("BPC actual CPU1");
        listaBPC = new ListView<>();
        listaBPC.getItems().addAll("PC", "IR", "AC", "ETC");
        VBox boxBPC = new VBox(10, lblBPC, listaBPC);
        boxBPC.setPadding(new Insets(10));
        boxBPC.setPrefWidth(200);

        // ====== Memoria y Disco ======
        Label lblMem = new Label("Memoria");
        tablaMemoria = new TableView<>();
        tablaMemoria.setPlaceholder(new Label("Tabla sin columnas"));
        VBox panelMem = new VBox(5, lblMem, tablaMemoria);
        panelMem.setPadding(new Insets(10));

        Label lblDisk = new Label("Disco");
        tablaDisco = new TableView<>();
        tablaDisco.setPlaceholder(new Label("Tabla sin columnas"));
        VBox panelDisk = new VBox(5, lblDisk, tablaDisco);
        panelDisk.setPadding(new Insets(10));

        HBox panelTablas = new HBox(20, panelMem, panelDisk);

        // ====== Pantalla (entrada/salida) ======
        Label lblPantalla = new Label("Pantalla");
        pantallaEntrada = new TextField();
        pantallaEntrada.setPromptText(">> Ingresar valor:");
        pantallaSalida = new TextArea();
        pantallaSalida.setEditable(false);
        VBox panelPantalla = new VBox(5, lblPantalla, pantallaEntrada, pantallaSalida);
        panelPantalla.setPadding(new Insets(10));
        panelPantalla.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        panelPantalla.setPrefHeight(160);

        // ====== Root ======
        BorderPane root = new BorderPane();
        root.setTop(barra);
        root.setLeft(izq);
        root.setCenter(boxBPC);
        root.setRight(panelTablas);
        root.setBottom(panelPantalla);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        // ====== “BUS de pantalla” inline (sin archivos nuevos) ======


        // ====== Handlers ======

        // Crear PC: uso tu CrearPCDialog (ajusta el constructor que tengas)
        btnCrearPC.setOnAction(e -> {
            try {
                // Si tu CrearPCDialog recibe (Controlador, Stage):
                CrearPCDialog dlg = new CrearPCDialog(controlador, primaryStage);
                // Si tu versión recibe además BUSPantalla, usa:
                // CrearPCDialog dlg = new CrearPCDialog(controlador, busPantalla, primaryStage);

                dlg.showAndWait().ifPresent(cfg -> {
                    escribir("PC creada → RAM " + cfg.getRamMB() + " MB, Almacenamiento " + cfg.getAlmacenamientoMB() + " MB");
                    refrescarUITrasCarga(); // pinta reg/mem/disk iniciales
                });
            } catch (Exception ex) {
                mostrarError("No se pudo crear la PC", ex.getMessage());
            }
        });

        // Cargar archivos → llama a Controlador.CargarPrograma y refresca UI
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar programa");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Programas (*.asm, *.txt, *.bin)", "*.asm", "*.txt", "*.bin"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        btnCargar.setOnAction(ev -> {
            File f = fc.showOpenDialog(primaryStage);
            if (f == null) return;
            try {
                controlador.CargarPrograma(f);
                escribir("Programa cargado: " + f.getName());
                refrescarUITrasCarga();
            } catch (Exception ex) {
                mostrarError("No se pudo cargar el programa", ex.getMessage());
            }
        });

        // Ejecutar (un paso) → EjecutarInstruccion y refrescar
        btnEjecutar.setOnAction(ev -> {
            try {
                controlador.EjecutarInstruccion();
                refrescarUITrasCarga();
            } catch (Exception ex) {
                mostrarError("Error al ejecutar instrucción", ex.getMessage());
            }
        });

        // Paso a paso = igual que ejecutar en tu arquitectura
        btnPaso.setOnAction(ev -> {
            try {
                controlador.EjecutarInstruccion();
                refrescarUITrasCarga();
            } catch (Exception ex) {
                mostrarError("Error al ejecutar instrucción", ex.getMessage());
            }
        });

        // Limpiar: limpiamos la vista (no toco tu Controlador)
        btnLimpiar.setOnAction(ev -> {
            listaBPC.getItems().clear();
            tablaMemoria.getItems().clear();
            tablaDisco.getItems().clear();
            pantallaSalida.clear();
            pantallaEntrada.clear();
        });

        // Estadísticas: muestra conteo básico desde la memoria/almacenamiento actuales
        btnEstadisticas.setOnAction(ev -> {
            var mem = controlador.TraerMemoriaActual();
            var disk = controlador.TraerAlmacenamiento();
            Alert a = new Alert(Alert.AlertType.INFORMATION,
                    "Entradas en Memoria: " + mem.size() + "\nEntradas en Disco: " + disk.size(),
                    ButtonType.OK);
            a.setTitle("Estadísticas");
            a.setHeaderText("Estado actual");
            a.showAndWait();
        });
    }

    // ============ Helpers de pintado ============

    /** Refresca registros, memoria y disco usando el Controlador. */
    private void refrescarUITrasCarga() {
        try {
            // Registros
            Map<String, String> regs = controlador.TraerRegistros();
            var items = regs.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(e -> e.getKey() + " = " + e.getValue())
                    .collect(Collectors.toList()); 
            listaBPC.getItems().setAll(items);
        } catch (Exception ignored) {}

        // Memoria y Disco
        cargarTablaKV(tablaMemoria, controlador.TraerMemoriaActual());
        cargarTablaKV(tablaDisco,   controlador.TraerAlmacenamiento());
    }

    /** Pinta un Map<K,V> en una TableView de dos columnas (Pos/Valor). */
    private void cargarTablaKV(TableView<KV> table, Map<String, String> data) {
        if (table.getColumns().isEmpty()) {
            TableColumn<KV, String> cPos   = new TableColumn<>("Pos");
            TableColumn<KV, String> cValor = new TableColumn<>("Valor");
            cPos.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().k));
            cValor.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().v));
            cPos.setPrefWidth(120);
            cValor.setPrefWidth(240);
            table.getColumns().setAll(cPos, cValor);
        }
        var rows = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> new KV(e.getKey(), e.getValue()))
                .collect(Collectors.toList()); 
        table.getItems().setAll(rows);
    }

    /** Modelo mínimo para la tabla (sin crear archivos). */
    private static class KV {
        final String k, v;
        KV(String k, String v) { this.k = k; this.v = v; }
    }

    /** Añade texto a la “pantalla” de salida. */
    private void escribir(String texto) {
        pantallaSalida.appendText(texto + "\n");
    }

    private void mostrarError(String header, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR, content, ButtonType.OK);
        a.setTitle("Error");
        a.setHeaderText(header);
        a.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}
