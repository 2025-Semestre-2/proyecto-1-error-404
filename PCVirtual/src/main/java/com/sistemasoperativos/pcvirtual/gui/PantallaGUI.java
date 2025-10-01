package com.sistemasoperativos.pcvirtual.gui;

import com.sistemasoperativos.pcvirtual.controlador.Controlador;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Ventana principal del simulador (Proyecto 1 de SO).
 * <p>
 * Muestra: barra de acciones; panel izquierdo con "Cargar archivos" y una tabla
 * de Procesos/Estados; panel central con registros del BCP actual; panel derecho
 * con tablas de Memoria y Disco; y la sección inferior "Pantalla" con entrada/salida.
 * <br>
 * Incluye la acción <strong>Crear PC</strong> que abre un diálogo secundario
 * ( {@link CrearPCDialog} ) para capturar RAM y Almacenamiento; dicho diálogo
 * invoca a {@link Controlador#CrearPC(int, int)} y opcionalmente devuelve un
 * {@link ConfigPC} con los datos ingresados para mostrar feedback o refrescar la UI.
 * </p>
 */
public class PantallaGUI extends Application {

    /** Controlador principal de la aplicación. */
    private final Controlador controlador = new Controlador();

    /** Área de salida de la “Pantalla” virtual. */
    private TextArea pantallaSalida;

    /** Campo de entrada de la “Pantalla” virtual. */
    private TextField pantallaEntrada;

    /**
     * Punto de entrada de JavaFX. Construye el layout principal y cablea acciones.
     *
     * @param primaryStage escenario principal
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Proyecto 1 de SO");

        // ---------------------------------------------------------------------
        // Barra superior de acciones
        // ---------------------------------------------------------------------
        HBox barraAcciones = new HBox(15);
        barraAcciones.setPadding(new Insets(10));
        barraAcciones.setAlignment(Pos.CENTER);

        Button btnCrearPC      = new Button("Crear PC");   // NUEVO
        Button btnEjecutar     = new Button("Ejecutar");
        Button btnPaso         = new Button("Paso a paso");
        Button btnLimpiar      = new Button("Limpiar");
        Button btnEstadisticas = new Button("Estadísticas");

        barraAcciones.getChildren().addAll(
                btnCrearPC, btnEjecutar, btnPaso, btnLimpiar, btnEstadisticas
        );

        // ---------------------------------------------------------------------
        // Panel izquierdo: “Cargar archivos” + tabla de procesos/estados
        // ---------------------------------------------------------------------
        VBox panelIzq = new VBox(10);
        panelIzq.setPadding(new Insets(10));
        panelIzq.setPrefWidth(180);

        Button btnCargar = new Button("Cargar archivos");
        TableView<String> tablaProcesos = new TableView<>();
        tablaProcesos.setPlaceholder(new Label("Procesos / Estados"));
        VBox.setVgrow(tablaProcesos, Priority.ALWAYS);

        panelIzq.getChildren().addAll(btnCargar, tablaProcesos);

        // ---------------------------------------------------------------------
        // Panel central: BPC actual (lista de registros)
        // ---------------------------------------------------------------------
        VBox panelBPC = new VBox(10);
        panelBPC.setPadding(new Insets(10));
        panelBPC.setPrefWidth(150);

        Label lblBPC = new Label("BPC actual CPU1");
        ListView<String> listaBPC = new ListView<>();
        listaBPC.getItems().addAll("PC", "IR", "AC", "ETC");

        panelBPC.getChildren().addAll(lblBPC, listaBPC);

        // ---------------------------------------------------------------------
        // Panel derecho: Memoria y Disco (tablas)
        // ---------------------------------------------------------------------
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

        // ---------------------------------------------------------------------
        // Panel inferior: “Pantalla” (entrada + salida)
        // ---------------------------------------------------------------------
        Label lblPantalla = new Label("Pantalla");
        pantallaEntrada = new TextField();
        pantallaEntrada.setPromptText(">> Ingresar valor:");
        pantallaSalida = new TextArea();
        pantallaSalida.setEditable(false);

        VBox panelPantalla = new VBox(5, lblPantalla, pantallaEntrada, pantallaSalida);
        panelPantalla.setPadding(new Insets(10));
        panelPantalla.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        panelPantalla.setPrefHeight(150);

        // ---------------------------------------------------------------------
        // Layout principal
        // ---------------------------------------------------------------------
        BorderPane root = new BorderPane();
        root.setTop(barraAcciones);
        root.setLeft(panelIzq);
        root.setCenter(panelBPC);
        root.setRight(panelTablas);
        root.setBottom(panelPantalla);

        Scene scene = new Scene(root, 950, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        // ---------------------------------------------------------------------
        // Acciones
        // ---------------------------------------------------------------------

        // “Crear PC”: abre la ventana secundaria y recibe RAM/Disco.
        btnCrearPC.setOnAction(e -> {
            CrearPCDialog dlg = new CrearPCDialog(controlador, primaryStage);
            Optional<ConfigPC> res = dlg.showAndWait();

            // Feedback opcional (además del llamado interno al controlador):
            res.ifPresent(cfg ->
                    escribir("PC creada → RAM: " + cfg.getRamMB() +
                             " MB, Almacenamiento: " + cfg.getAlmacenamientoMB() + " MB")
            );
        });

        // Ejemplo simple para “Limpiar”
        btnLimpiar.setOnAction(e -> pantallaSalida.clear());
    }

    /**
     * Agrega una línea de texto al área de salida de la “Pantalla” virtual.
     *
     * @param texto contenido a mostrar
     */
    public void escribir(String texto) {
        pantallaSalida.appendText(texto + "\n");
    }

    /**
     * Lee el contenido actual del campo de entrada de la “Pantalla” virtual y lo limpia.
     *
     * @return el texto introducido por el usuario (o cadena vacía si no había nada)
     */
    public String leer() {
        String texto = pantallaEntrada.getText();
        pantallaEntrada.clear();
        return texto;
    }

    /**
     * Método main para lanzar la aplicación JavaFX.
     *
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
