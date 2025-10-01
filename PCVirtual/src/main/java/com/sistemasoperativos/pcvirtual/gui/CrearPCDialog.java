/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

import java.util.Optional;

/**
 *
 * @author males
 */

public class CrearPCDialog extends Dialog<ConfigPC> {

    public CrearPCDialog(Window owner) {
        setTitle("Crear PC");
        setHeaderText("Configure la memoria para el PC");

        if (owner != null) getDialogPane().getScene();
        getDialogPane().getButtonTypes().addAll(
                new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE),
                ButtonType.CANCEL
        );

        // --- UI ---
        TextField tfRam = new TextField("1024");       
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

        // Validación básica: solo enteros positivos
        final Button btnCrear = (Button) getDialogPane().lookupButton(
                getDialogPane().getButtonTypes().stream()
                        .filter(bt -> bt.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                        .findFirst().orElse(ButtonType.OK)
        );
        btnCrear.addEventFilter(javafx.event.ActionEvent.ACTION, evt -> {
            if (!esEnteroPositivo(tfRam.getText()) || !esEnteroPositivo(tfDisco.getText())) {
                new Alert(Alert.AlertType.ERROR,
                        "Ingrese números enteros positivos para RAM y Almacenamiento.")
                        .showAndWait();
                evt.consume(); // no cerrar
            }
        });

        setResultConverter(bt -> {
            if (bt.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                int ram = Integer.parseInt(tfRam.getText().trim());
                int disco = Integer.parseInt(tfDisco.getText().trim());
                return new ConfigPC(ram, disco);
            }
            return null;
        });
    }

    private boolean esEnteroPositivo(String s) {
        try {
            return Integer.parseInt(s.trim()) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /** Utilidad para uso rápido */
    public static Optional<ConfigPC> show(Window owner) {
        return new CrearPCDialog(owner).showAndWait();
    }
}
