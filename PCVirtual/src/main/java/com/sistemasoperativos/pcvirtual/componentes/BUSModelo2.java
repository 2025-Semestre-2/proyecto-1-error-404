/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.componentes;

import com.sistemasoperativos.pcvirtual.procesos.Planificador;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andre
 */
public class BUSModelo2 extends BUSModelo1 implements BUS2 {
    
    private Planificador PlanificadorAsignado;
    private Almacenamiento AlmacenamientoActual;
    
    public BUSModelo2(RAM ram, CPU cpu, Planificador planificador, Almacenamiento almacenamiento) {
        super(ram, cpu);
        PlanificadorAsignado = planificador;
        AlmacenamientoActual = almacenamiento;
    }

    @Override
    public String EscribirAlmacenamiento(List<String> programa) throws Exception{
        return AlmacenamientoActual.GuardarPrograma(programa);
    }

    @Override
    public List<String> LeerAlmacenamiento(String direccion) throws Exception{
        return AlmacenamientoActual.BuscarPrograma(direccion);
    }

    @Override
    public void SolicitarNuevoPrograma() throws Exception {
        PlanificadorAsignado.obtenerSiguiente();
    }
    
    @Override
    public Map<String, String> TraerMemoriaRAM(){
        return RAMInstalada.TraerMemoriaRAM();
    }
    
    public Map<String, String> TraerAlmacenamiento(){
        return AlmacenamientoActual.TraerAlmacenamiento();
    }
}
