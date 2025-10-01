/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.gui;

/**
 *
 * @author males
 */
/** Resultado del diálogo Crear PC. Tamaños en MB (o en la unidad que uses). */
/**
 * Representa la configuración básica de una PC virtual para el simulador.
 * <p>
 * Actualmente solo modela las capacidades de memoria principal (RAM) y
 * de almacenamiento secundario (disco), expresadas en megabytes (MB).
 * </p>
 */
public class ConfigPC {

    /** Capacidad de memoria principal (RAM */
    private final int ramMB;

    /** Capacidad de almacenamiento secundario (disco). */
    private final int almacenamientoMB;

    /**
     * Crea una nueva configuración de PC.
     *
     * @param ramMB            tamaño de la memoria RAM 
     * @param almacenamientoMB tamaño del almacenamiento
     */
    public ConfigPC(int ramMB, int almacenamientoMB) {
        this.ramMB = ramMB;
        this.almacenamientoMB = almacenamientoMB;
    }

    /**
     * Devuelve la capacidad de RAM.
     *
     * @return cantidad de memoria RAM en MB
     */
    public int getRamMB() {
        return ramMB;
    }

    /**
     * Devuelve la capacidad de almacenamiento.
     *
     * @return cantidad de almacenamiento en MB
     */
    public int getAlmacenamientoMB() {
        return almacenamientoMB;
    }
}