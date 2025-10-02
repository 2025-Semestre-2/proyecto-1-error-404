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

    public GestorProcesos(Planificador planificador) {
        this.planificador = planificador;
    }

    // Crear proceso y mandarlo a la cola de listos
    public void crearProceso() throws Exception {
        planificador.agregarProceso();
    }

    public BCP getProcesoActual() {
        return planificador.ObtenerBCPActual();
    }

    public Planificador getPlanificador() {
        return planificador;
    }
}

