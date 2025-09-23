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
public abstract class InstruccionComun {
    protected Map<String, String> Registros;
    protected Conversor ConversorAsignado;
    protected String Param1;
    
    public InstruccionComun(Map<String, String> registros, Conversor conversor){
        Registros = registros;
        ConversorAsignado = conversor;
    }
    
    protected void IrSiguienteInstruccion(){
        String instruccionActual = Registros.get("00010");
        int resultado = ConversorAsignado.ConvertirBitsAInteger(instruccionActual) + 1;
        String siguienteInstruccion = ConversorAsignado.ConvertirIntegerABits(resultado);
        Registros.put("00010", siguienteInstruccion);
    }
    
    protected abstract void Desestructurar(String lineaBinaria);
}
