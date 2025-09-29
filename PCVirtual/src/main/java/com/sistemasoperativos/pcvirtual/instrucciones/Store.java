/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.instrucciones;

import com.sistemasoperativos.pcvirtual.componentes.BUS;
import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import java.util.Map;

/**
 *
 * @author andre
 */
public class Store extends InstruccionComunUnParametro implements Instruccion{
    
    BUS Bus;

    public Store(Map<String, String> registros, Conversor conversor, int peso, BUS bus) {
        super(conversor, peso);
        Bus = bus;
    }

    @Override
    public void EjecutarInstruccion(String instruccion, Map<String, String> registros) throws Exception {
        Registros = registros;
        AplicarPeso();
        Desestructurar(instruccion);
        String dato = Registros.get("00001");
        String direccion = Registros.get(Param1);
        Bus.EscribirDatoRAM(direccion, dato);
        IrSiguienteInstruccion();
    }
}
