/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.procesos;

import com.sistemasoperativos.pcvirtual.componentes.BUS2;
import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 *
 * @author males
 */

public class Planificador {
    private final ColaProcesos colaListos;
    private BUS2 BUSAsignado;
    private LinkedList<String> DireccionesProgramas;
    private LinkedList<String> NombresProgramas;
    private final int DireccionBase = 100;
    private Conversor ConversorAsignado;
    private int CantidadProgramas;
    private BCP BCPActual;

    public Planificador(LinkedList<String> direccionesPrograma, LinkedList<String> nombresProgramas) {
        this.colaListos = new ColaProcesos();
        BUSAsignado = null;
        ConversorAsignado = new Conversor();
        CantidadProgramas = 0;
        DireccionesProgramas = direccionesPrograma;
        NombresProgramas = nombresProgramas;
    }

    public void agregarProceso() throws Exception {
        System.out.println(DireccionesProgramas.toString());
        System.out.println(NombresProgramas.toString());
        while(!DireccionesProgramas.isEmpty() && !colaListos.estaLleno()){
            String direccionMemoriaPrograma = DireccionesProgramas.removeLast();
            List<String> programa = BUSAsignado.LeerAlmacenamiento(direccionMemoriaPrograma);
            BCP proceso = new BCP(CantidadProgramas, NombresProgramas.removeLast(), programa.size());
            EscribirPrograma(programa, proceso);
            proceso.marcarPreparado();
            colaListos.agregar(proceso, BUSAsignado);
            CantidadProgramas++;
        }
    }
    
    private void EscribirPrograma(List<String> programa, BCP bcp) throws Exception{
        int tamanoPrograma = programa.size();
        int direccion = BuscarEspacio(tamanoPrograma + 5);
        for(int indice = 0; indice < tamanoPrograma; indice++){
            String direccionBits = ConversorAsignado.ConvertirIntegerABits(indice + direccion);
            String dato = programa.get(indice);
            BUSAsignado.EscribirDatoRAM(direccionBits, dato);
        }
        String base = ConversorAsignado.ConvertirIntegerABits(direccion);
        bcp.setBS(base);
        String limite = ConversorAsignado.ConvertirIntegerABits(direccion + tamanoPrograma + 4);
        bcp.setLimite(limite);
        String sp = ConversorAsignado.ConvertirIntegerABits(direccion + tamanoPrograma);
        bcp.setSP(sp);
        String ir = programa.get(0);
        bcp.setIR(ir);
        String pc = ConversorAsignado.ConvertirIntegerABits(direccion + 1);
        bcp.setPC(pc);
    }
    
    private int BuscarEspacio(int tamano){
        Queue<BCP> procesos = colaListos.getProcesosOrdenadorPorMemoria();
        int direccionLimite = DireccionBase;
        while(!procesos.isEmpty()){
            BCP bcp = procesos.poll();
            String direccionBase = bcp.getBS();
            int direccionBaseEntero = ConversorAsignado.ConvertirBitsAInteger(direccionBase);
            if(direccionBaseEntero - direccionLimite >= tamano)
                return direccionLimite;
            direccionLimite = direccionBaseEntero;
        }
        return direccionLimite;
    }

    public BCP obtenerSiguiente() throws Exception {
        BCP bcp = colaListos.obtener(); // FCFS
        BUSAsignado.AsignarRegistrosCPU(bcp.getRegistros());
        BCPActual = bcp;
        return bcp;
    }

    public boolean hayProcesos() {
        return !colaListos.estaVacia();
    }

    public ColaProcesos getColaListos() {
        return colaListos;
    }
    
    public void AsignarBUS(BUS2 bus){
        BUSAsignado = bus;
    }
    
    public BCP ObtenerBCPActual(){
        return BCPActual;
    }
}
