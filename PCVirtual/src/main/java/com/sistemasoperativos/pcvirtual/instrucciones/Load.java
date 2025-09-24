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
public class Load extends InstruccionComunUnParametro implements Instruccion{
    
    BUS Bus;

    public Load(Map<String, String> registros, Conversor conversor, int peso, BUS bus) {
        super(registros, conversor, peso);
        Bus = bus;
    }

    @Override
    public void EjecutarInstruccion(String instruccion) throws Exception {
        AplicarPeso();
        Desestructurar(instruccion);
        String direccion = Registros.get(Param1);
        String dato = Bus.LeerDatoRAM(direccion);
        Registros.put("00001", dato);
        IrSiguienteInstruccion();
    }
    
}
