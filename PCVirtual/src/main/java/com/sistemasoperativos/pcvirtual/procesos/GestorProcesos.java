/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.procesos;

/**
 *
 * @author males
 */


public class GestorProcesos {
    private final Planificador planificador;
    private BCP procesoActual;

    public GestorProcesos() {
        this.planificador = new Planificador();
        this.procesoActual = null;
    }

    // Crear proceso y mandarlo a la cola de listos
    public void crearProceso(int id, String nombre, int prioridad, int base, int limite) {
        BCP bcp = new BCP(id, nombre, prioridad, base, limite);
        bcp.marcarNuevo();
        planificador.agregarProceso(bcp);
    }

    // Despacho (cambio de contexto)
    public void despachar() {
        if (procesoActual != null) {
            procesoActual.marcarPreparado();
            planificador.agregarProceso(procesoActual);
        }
        procesoActual = planificador.obtenerSiguiente();
        if (procesoActual != null) {
            procesoActual.marcarEjecucion();
        }
    }

    public void finalizarActual() {
        if (procesoActual != null) {
            procesoActual.marcarFinalizado();
            procesoActual = null;
        }
    }

    public BCP getProcesoActual() {
        return procesoActual;
    }

    public Planificador getPlanificador() {
        return planificador;
    }
}

