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
public class Mov extends InstruccionComunDosParametros implements Instruccion{

    public Mov(Map<String, String> registros, Conversor conversor) {
        super(registros, conversor);
    }

    

    @Override
    public void EjecutarInstruccion(String instruccion) throws Exception {
        Desestructurar(instruccion);
        if(Param2.length() == 8){
            Registros.put(Param1, Param2);
        }
        else{
            String dato = Registros.get(Param2);
            Registros.put(Param1, dato);
        }
    }
    
}
