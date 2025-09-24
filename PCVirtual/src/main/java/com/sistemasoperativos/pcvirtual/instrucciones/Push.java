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
public class Push extends InstruccionComunUnParametro implements Instruccion{
    
    BUS BUSAsignado;

    public Push(Map<String, String> registros, Conversor conversor, int peso, BUS bus) {
        super(registros, conversor, peso);
        BUSAsignado = bus;
    }

    @Override
    public void EjecutarInstruccion(String instruccion) throws Exception {
        AplicarPeso();
        Desestructurar(instruccion);
        String dato = Registros.get(Param1);
        String direccion = Registros.get("01000");
        int nuevaDireccion = ConversorAsignado.ConvertirBitsAInteger(direccion) + 1;
        String nuevaDireccionBits = ConversorAsignado.ConvertirIntegerABits(nuevaDireccion);
        Registros.put("01000", nuevaDireccionBits);
        BUSAsignado.EscribirDatoRAM(direccion, dato);
        IrSiguienteInstruccion();
    }
    
}
