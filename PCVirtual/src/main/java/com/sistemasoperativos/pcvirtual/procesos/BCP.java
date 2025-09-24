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
    private int PC;   // Contador de programa
    private int AC;   // Acumulador
    private int AX, BX, CX, DX; // otros registros
    private int IR;   // Registro de instrucción

    // -------- Pila --------
    private final int[] pila;
    private int sp; // stack pointer

    // -------- Información contable --------
    private String cpuAsignado;
    private long tiempoInicio;
    private long tiempoEjecutado;

    // -------- Información de E/S --------
    private final List<String> archivosAbiertos;

    // -------- Enlace al siguiente BCP (lista enlazada) --------
    private BCP siguiente;

    // -------- Memoria --------
    private int base;   // Dirección de inicio
    private int limite; // Tamaño del proceso (alcance)

    public BCP(int id, String nombre, int prioridad, int base, int limite) {
        this.id = id;
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.base = base;
        this.limite = limite;
        this.estado = EstadoBCP.NUEVO;

        // Inicialización de registros
        this.PC = 0;
        this.AC = 0;
        this.AX = 0;
        this.BX = 0;
        this.CX = 0;
        this.DX = 0;
        this.IR = 0;

        // Pila fija de tamaño 5
        this.pila = new int[5];
        this.sp = -1; // stack vacío

        // Info contable
        this.cpuAsignado = "CPU0"; // por defecto
        this.tiempoInicio = System.currentTimeMillis();
        this.tiempoEjecutado = 0;

        // Archivos abiertos vacíos
        this.archivosAbiertos = new ArrayList<>();

        // Sin siguiente al inicio
        this.siguiente = null;
    }

    // ----------- Operaciones con pila -----------
    public void push(int valor) throws Exception {
        if (sp == pila.length - 1) {
            throw new Exception("Error de desbordamiento de pila en proceso " + id);
        }
        pila[++sp] = valor;
    }

    public int pop() throws Exception {
        if (sp == -1) {
            throw new Exception("Pila vacía en proceso " + id);
        }
        return pila[sp--];
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

    // ----------- Getters y Setters ----------- 
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getPrioridad() { return prioridad; }
    public EstadoBCP getEstado() { return estado; }

    public int getPC() { return PC; }
    public void setPC(int PC) { this.PC = PC; }

    public int getAC() { return AC; }
    public void setAC(int AC) { this.AC = AC; }

    public int getAX() { return AX; }
    public void setAX(int AX) { this.AX = AX; }

    public int getBX() { return BX; }
    public void setBX(int BX) { this.BX = BX; }

    public int getCX() { return CX; }
    public void setCX(int CX) { this.CX = CX; }

    public int getDX() { return DX; }
    public void setDX(int DX) { this.DX = DX; }

    public int getIR() { return IR; }
    public void setIR(int IR) { this.IR = IR; }

    public String getCpuAsignado() { return cpuAsignado; }
    public void setCpuAsignado(String cpuAsignado) { this.cpuAsignado = cpuAsignado; }

    public long getTiempoInicio() { return tiempoInicio; }
    public long getTiempoEjecutado() { return tiempoEjecutado; }
    public void aumentarTiempoEjecutado(long delta) { this.tiempoEjecutado += delta; }

    public List<String> getArchivosAbiertos() { return archivosAbiertos; }

    public BCP getSiguiente() { return siguiente; }
    public void setSiguiente(BCP siguiente) { this.siguiente = siguiente; }

    public int getBase() { return base; }
    public int getLimite() { return limite; }

    @Override
    public String toString() {
        return "Proceso " + id + " [" + nombre + "] - Estado: " + estado +
                " - PC=" + PC + " AC=" + AC + " AX=" + AX + " BX=" + BX +
                " CX=" + CX + " DX=" + DX;
    }
}
