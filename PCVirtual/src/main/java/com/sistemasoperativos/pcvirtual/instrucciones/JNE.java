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
public class JNE extends JMP implements Instruccion{
    
    public JNE(Map<String, String> registros, Conversor conversor) {
        super(registros, conversor);
    }
    
    @Override
    public void EjecutarInstruccion(String instruccion) throws Exception {
        Desestructurar(instruccion);
        String banderaBits = Registros.get("00111");
        int bandera = ConversorAsignado.ConvertirBitsAInteger(banderaBits);
        if(bandera != 1){
            super.EjecutarInstruccion(instruccion);
        }
        else{
            IrSiguienteInstruccion();
        }
    }
}
