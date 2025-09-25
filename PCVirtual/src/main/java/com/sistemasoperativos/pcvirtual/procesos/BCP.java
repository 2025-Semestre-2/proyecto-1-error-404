/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.procesos;

/**
 *
 * @author males
 */

import java.util.ArrayList;
import java.util.List;

public class BCP {
    private final int id;
    private final String nombre;
    private final int prioridad;
    private EstadoBCP estado;

    // -------- Registros --------
    private String PC;   // Contador de programa
    private String AC;   // Acumulador
    private String AX, BX, CX, DX; // otros registros
    private String IR;   // Registro de instrucción

    // -------- Pila --------
    private String SP; // stack pointer

    // -------- Información contable --------
    private String cpuAsignado;
    private long tiempoInicio;
    private long tiempoEjecutado;

    // -------- Información de E/S --------
    private final List<String> archivosAbiertos;

    // -------- Enlace al siguiente BCP (lista enlazada) --------
    private BCP siguiente;

    // -------- Memoria --------
    private String base;   // Dirección de inicio
    private String limite; // Tamaño del proceso (alcance)

    public BCP(int id, String nombre, int prioridad, String base, String limite) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.base = base;
        this.limite = limite;
        this.estado = EstadoBCP.NUEVO;

        // Inicialización de registros
        this.PC = "00000000";
        this.AC = "00000000";
        this.AX = "00000000";
        this.BX = "00000000";
        this.CX = "00000000";
        this.DX = "00000000";
        this.IR = "00000000";
        this.SP = "00000000";

        // Info contable
        this.cpuAsignado = "CPU0"; // por defecto
        this.tiempoInicio = System.currentTimeMillis();
        this.tiempoEjecutado = 0;

        // Archivos abiertos vacíos
        this.archivosAbiertos = new ArrayList<>();

        // Sin siguiente al inicio
        this.siguiente = null;
    }

    // ----------- Manejo de archivos ----------- 
    public void abrirArchivo(String nombreArchivo) {
        archivosAbiertos.add(nombreArchivo);
    }

    public void cerrarArchivo(String nombreArchivo) {
        archivosAbiertos.remove(nombreArchivo);
    }

    // ----------- Métodos de cambio de estado -----------
    public void marcarNuevo() { this.estado = EstadoBCP.NUEVO; }
    public void marcarPreparado() { this.estado = EstadoBCP.LISTO; }
    public void marcarEjecucion() { this.estado = EstadoBCP.EJECUTANDO; }
    public void marcarEsperando() { this.estado = EstadoBCP.ESPERANDO; }
    public void marcarFinalizado() { this.estado = EstadoBCP.FINALIZADO; }

    

    @Override
    public String toString() {
        return "Proceso " + id + " [" + nombre + "] - Estado: " + estado +
                " - PC=" + PC + " AC=" + AC + " AX=" + AX + " BX=" + BX +
                " CX=" + CX + " DX=" + DX;
    }

    public EstadoBCP getEstado() {
        return estado;
    }

    public void setEstado(EstadoBCP estado) {
        this.estado = estado;
    }

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public String getAC() {
        return AC;
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public String getAX() {
        return AX;
    }

    public void setAX(String AX) {
        this.AX = AX;
    }

    public String getBX() {
        return BX;
    }

    public void setBX(String BX) {
        this.BX = BX;
    }

    public String getCX() {
        return CX;
    }

    public void setCX(String CX) {
        this.CX = CX;
    }

    public String getDX() {
        return DX;
    }

    public void setDX(String DX) {
        this.DX = DX;
    }

    public String getIR() {
        return IR;
    }

    public void setIR(String IR) {
        this.IR = IR;
    }

    public String getSP() {
        return SP;
    }

    public void setSP(String SP) {
        this.SP = SP;
    }

    public String getCpuAsignado() {
        return cpuAsignado;
    }

    public void setCpuAsignado(String cpuAsignado) {
        this.cpuAsignado = cpuAsignado;
    }

    public long getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(long tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public long getTiempoEjecutado() {
        return tiempoEjecutado;
    }

    public void setTiempoEjecutado(long tiempoEjecutado) {
        this.tiempoEjecutado = tiempoEjecutado;
    }

    public BCP getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(BCP siguiente) {
        this.siguiente = siguiente;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }
    
    
}
