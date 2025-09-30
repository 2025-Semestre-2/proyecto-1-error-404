/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.instrucciones;

import com.sistemasoperativos.pcvirtual.componentes.BUS2;
import com.sistemasoperativos.pcvirtual.componentes.BUSPantalla;
import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import java.util.Map;

/**
 *
 * @author andre
 */
public class Int extends InstruccionComunUnParametro implements Instruccion{
    
    BUSPantalla BusPantalla;
    BUS2 BUSAsignado;

    public Int(Conversor conversor, int peso,BUSPantalla busPantalla, BUS2 bus) {
        super(conversor, peso);
        BusPantalla = busPantalla;
    }

    @Override
    public void EjecutarInstruccion(String instruccion, Map<String, String> registros) throws Exception {
        Registros = registros;
        if(AplicarPeso())
            return;
        Desestructurar(instruccion);
        switch (Param1) {
            case "00000":
                EjecutarINT09H();
                break;
            case "00001":
                EjecutarINT10H();
                break;
            case "00010":
                EjecutarINT20H();
                break;
            default:
                break;
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
    
    private void EjecutarINT20H() throws Exception{
        BUSAsignado.SolicitarNuevoPrograma();
    }
    
}
