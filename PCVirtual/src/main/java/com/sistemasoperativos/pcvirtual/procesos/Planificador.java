/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.procesos;


/**
 *
 * @author males
 */

public class Planificador {
    private final ColaProcesos colaListos;

    public Planificador() {
        this.colaListos = new ColaProcesos();
    }

    public void agregarProceso(BCP proceso) {
        proceso.marcarPreparado();
        colaListos.agregar(proceso);
    }

    public BCP obtenerSiguiente() {
        return colaListos.obtener(); // FCFS 
    }

    public boolean hayProcesos() {
        return !colaListos.estaVacia();
    }

    public ColaProcesos getColaListos() {
        return colaListos;
    }
}
