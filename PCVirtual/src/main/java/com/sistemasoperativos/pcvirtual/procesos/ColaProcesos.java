/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.procesos;

/**
 *
 * @author males
 */

import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ColaProcesos {
    private final Queue<BCP> cola;
    private Conversor ConversorAsignado;

    public ColaProcesos() {
        this.cola = new LinkedList<>();
        ConversorAsignado = new Conversor();
    }

    public void agregar(BCP proceso) {
        cola.add(proceso);
    }

    public BCP obtener() throws Exception {
        if(cola.isEmpty())
            throw new Exception("No hay procesos en la cola de procesos");
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }
    
    public boolean estaLleno(){
        return cola.size() >= 5;
    }

    public int size() {
        return cola.size();
    }
    
    public Queue<BCP> getProcesosOrdenadorPorMemoria(){
        List<BCP> BCPs = new LinkedList<>(cola);
        Queue<BCP> colaOrdenada = new LinkedList<>();
        while(!BCPs.isEmpty()){
            BCP bcpMenor = BCPs.get(0);
            for(BCP bcp : BCPs){
                String direccionMenor = bcpMenor.getBS();
                int direccionMenorEntero = ConversorAsignado.ConvertirBitsAInteger(direccionMenor);
                String direccion = bcp.getBS();
                int direccionEntero = ConversorAsignado.ConvertirBitsAInteger(direccion);
                if(direccionEntero < direccionMenorEntero){
                    bcpMenor = bcp;
                }
            }
            colaOrdenada.add(bcpMenor);
        }
        return colaOrdenada;
    }

    public Queue<BCP> getProcesos() {
        return cola;
    }
}

