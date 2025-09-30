/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.instrucciones;

import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import java.util.Map;

/**
 *
 * @author andre
 */
public class Dec extends InstruccionComunUnParametro implements Instruccion{

    public Dec(Conversor conversor, int peso) {
        super(conversor, peso);
    }

    @Override
    public void EjecutarInstruccion(String instruccion, Map<String, String> registros) throws Exception {
        Registros = registros;
        if(AplicarPeso())
            return;
        AplicarPeso();
        if(instruccion.length() == 5){
            String dato = Registros.get("00001");
            int resultado = ConversorAsignado.ConvertirBitsAInteger(dato) - 1;
            String bits = ConversorAsignado.ConvertirIntegerABits(resultado);
            Registros.put("00001", bits);
        }
        else{
            Desestructurar(instruccion);
            String dato = Registros.get(Param1);
            int resultado = ConversorAsignado.ConvertirBitsAInteger(dato) - 1;
            String bits = ConversorAsignado.ConvertirIntegerABits(resultado);
            Registros.put(Param1, bits);
            IrSiguienteInstruccion();
        }
    }
    
}
