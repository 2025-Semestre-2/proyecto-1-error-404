/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.gui;
import com.sistemasoperativos.pcvirtual.controlador.Controlador;


import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;


import java.util.Optional;

/**
 *
 * @author males
 */

/**
 * Diálogo para configurar y crear una PC virtual.
 * <p>
 * Al pulsar <strong>Crear</strong>, valida los campos y llama a
 * {@link Controlador#CrearPC(int, int)} con los tamaños de RAM y
 * almacenamiento ingresados. Además, el diálogo devuelve un {@link ConfigPC}
 * como resultado para que la vista principal pueda mostrar feedback o refrescar
 * sus tablas si lo desea.
 * </p>
 */
public class CrearPCDialog extends Dialog<ConfigPC> {

    private final Controlador controlador;

    /**
     * @param controlador controlador de la aplicación para invocar {@code CrearPC}
     * @param owner       ventana propietaria (para posicionamiento/estilo del diálogo)
     */
    public CrearPCDialog(Controlador controlador, Window owner) {
        this.controlador = controlador;

        setTitle("Crear PC");
        setHeaderText("Configure la memoria para el PC");
        if (owner != null) {
            // asegura que el diálogo herede estilo/posición del owner
            initOwner(owner);
        }

        // --- Botones del diálogo ---
        ButtonType btCrear = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(btCrear, ButtonType.CANCEL);

        // --- Contenido del formulario ---
        TextField tfRam   = new TextField("1024");  
        TextField tfDisco = new TextField("8192");  

        tfRam.setPromptText("RAM (MB)");
        tfDisco.setPromptText("Almacenamiento (MB)");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(15));
        grid.addRow(0, new Label("Memoria RAM (MB):"), tfRam);
        grid.addRow(1, new Label("Almacenamiento (MB):"), tfDisco);

        getDialogPane().setContent(grid);

        // --- Handler del botón "Crear": valida y llama al controlador ---
        Button btnCrear = (Button) getDialogPane().lookupButton(btCrear);
        btnCrear.addEventFilter(javafx.event.ActionEvent.ACTION, evt -> {
            try {
                int ram   = parseEnteroPositivo(tfRam.getText());
                int disco = parseEnteroPositivo(tfDisco.getText());

                // Llamada directa al controlador
                controlador.CrearPC(ram, disco);

            } catch (IllegalArgumentException ex) {
                mostrarError("Entrada inválida", ex.getMessage());
                evt.consume(); // no cerrar el diálogo
            } catch (Exception ex) {
                mostrarError("No se pudo crear la PC",
                        "Error al crear la PC: " + ex.getMessage());
                evt.consume(); // no cerrar el diálogo
            }
        });

        // --- Resultado del diálogo ---
        setResultConverter(bt -> {
            if (bt == btCrear) {
                int ram   = parseEnteroPositivo(tfRam.getText());
                int disco = parseEnteroPositivo(tfDisco.getText());
                return new ConfigPC(ram, disco);
            }
            return null;
        });
    }

    /** Parsea un entero positivo o lanza {@link IllegalArgumentException}. */
    private int parseEnteroPositivo(String s) {
        try {
            int v = Integer.parseInt(s.trim());
            if (v <= 0) throw new NumberFormatException();
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Debe ingresar un entero positivo.");
        }
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert a = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        a.setHeaderText(titulo);
        a.showAndWait();
    }
}
