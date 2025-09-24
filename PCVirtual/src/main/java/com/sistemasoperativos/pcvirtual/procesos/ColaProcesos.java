/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.procesos;

/**
 *
 * @author males
 */

import java.util.LinkedList;
import java.util.Queue;

public class ColaProcesos {
    private final Queue<BCP> cola;

    public ColaProcesos() {
        this.cola = new LinkedList<>();
    }

    public void agregar(BCP proceso) {
        cola.add(proceso);
    }

    public BCP obtener() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int size() {
        return cola.size();
    }

    public Queue<BCP> getProcesos() {
        return cola;
    }
}

