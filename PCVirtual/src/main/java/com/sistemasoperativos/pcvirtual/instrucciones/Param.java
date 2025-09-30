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
public class Param extends InstruccionComunDosParametros implements Instruccion{

    private BUS BUSAsignado;
    private String Param3;
    
    public Param(Conversor conversor, int peso, BUS bus) {
        super(conversor, peso);
        BUSAsignado = bus;
    }

    @Override
    public void EjecutarInstruccion(String instruccion, Map<String, String> registros) throws Exception {
        Registros = registros;
        if(AplicarPeso())
            return;
        Desestructurar(instruccion);
        for(int seguimiento = 5; seguimiento < instruccion.length(); seguimiento += 5){
            String direccionBits = registros.get("01000");
            switch(seguimiento){
                case 5:
                    BUSAsignado.EscribirDatoRAM(direccionBits, Param1);
                case 10:
                    BUSAsignado.EscribirDatoRAM(direccionBits, Param2);
                case 15:
                    BUSAsignado.EscribirDatoRAM(direccionBits, Param3);
            }
            int direccion = ConversorAsignado.ConvertirBitsAInteger(direccionBits)+1;
            direccionBits = ConversorAsignado.ConvertirIntegerABits(direccion);
            registros.put("01000", direccionBits);
        }
    }
    
    public void Desesctructurar(String instruccion){
        Param1 = instruccion.substring(5, 10);
        if(instruccion.length() <= 10)
            return;
        Param2 = instruccion.substring(10, 15);
        if(instruccion.length() <= 15)
            return;
        Param3 = instruccion.substring(15, 20);
    }
}
