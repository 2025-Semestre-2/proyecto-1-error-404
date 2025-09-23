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
public class Pop extends InstruccionComunUnParametro implements Instruccion{

    BUS Bus;

    public Pop(Map<String, String> registros, Conversor conversor, BUS bus) {
        super(registros, conversor);
        Bus = bus;
    }

    @Override
    public void EjecutarInstruccion(String instruccion) throws Exception {
        Desestructurar(instruccion);
        String direccion = Registros.get("01000");
        int nuevaDireccion = ConversorAsignado.ConvertirBitsAInteger(direccion) - 1;
        String nuevaDireccionBits = ConversorAsignado.ConvertirIntegerABits(nuevaDireccion);
        Registros.put("01000", nuevaDireccionBits);
        String dato = Bus.LeerDatoCPU(nuevaDireccionBits);
        Registros.put(Param1, dato);
    }
    
}
