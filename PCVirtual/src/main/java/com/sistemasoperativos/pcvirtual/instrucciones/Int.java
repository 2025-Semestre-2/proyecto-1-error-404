/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.instrucciones;

import com.sistemasoperativos.pcvirtual.componentes.BUSPantalla;
import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import java.util.Map;

/**
 *
 * @author andre
 */
public class Int extends InstruccionComunUnParametro implements Instruccion{
    
    BUSPantalla BusPantalla;

    public Int(Map<String, String> registros, Conversor conversor, BUSPantalla busPantalla) {
        super(registros, conversor);
        BusPantalla = busPantalla;
    }

    @Override
    public void EjecutarInstruccion(String instruccion) throws Exception {
        Desestructurar(instruccion);
        if(Param1.equals("00000")){
            BusPantalla.Leer();
        }
    }
    
    private void EjecutarINT09H(){
        String dato = BusPantalla.Leer();
        if(!dato.isEmpty()){
            Registros.put("00110", dato);
            IrSiguienteInstruccion();
        }
    }
    
    private void EjecutarINT10H(){
        String dato = Registros.get("00110");
        BusPantalla.Escribir(dato);
        IrSiguienteInstruccion();
    }
    
    private void EjecutarINT20H(){
        
    }
    
}
