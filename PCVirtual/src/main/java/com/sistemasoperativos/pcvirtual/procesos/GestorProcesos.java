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

    public GestorProcesos(Planificador planificador) {
        this.planificador = planificador;
        this.procesoActual = null;
    }

    // Crear proceso y mandarlo a la cola de listos
    public void crearProceso() throws Exception {
        planificador.agregarProceso();
    }

    // Despacho (cambio de contexto)
    public void despachar() throws Exception {
        finalizarActual();
        procesoActual = planificador.obtenerSiguiente();
        if (procesoActual != null) {
            procesoActual.marcarEjecucion();
        }
    }

    private void finalizarActual() {
        if (procesoActual != null) {
            procesoActual.marcarFinalizado();
            procesoActual = null;
        }
    }

    public BCP getProcesoActual() {
        return planificador.ObtenerBCPActual();
    }

    public Planificador getPlanificador() {
        return planificador;
    }
}

