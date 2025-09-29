/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.componentes;

import com.sistemasoperativos.pcvirtual.instrucciones.Instruccion;
import java.util.Map;

/**
 *
 * @author andre
 */
public class CPUModelo2 implements CPU{
    
    private Map<String, Instruccion> Instrucciones;
    
    /**
     * PC = 00000
     * AC = 00001
     * IR = 00010
     * AX = 00011
     * BX = 00100
     * CX = 00101
     * DX = 00110
     * CP = 00111
     * SP = 01000
     */
    private Map<String, String> Registros;
    
    private BUS BUSAsignado;
    
    private int ContadorPeso;
    
    public CPUModelo2(Map<String, Instruccion> instrucciones, Map<String, String> registros){
        Instrucciones = instrucciones;
        Registros = null;
    }
    
    @Override
    public void EjecutarInstruccion() throws Exception {
        String instruccionBits = Registros.get("00010").substring(0, 5);
        if(!Instrucciones.containsKey(instruccionBits)){
            throw new Exception("La instrucción " + instruccionBits + " no existe");
        }
        Instruccion instruccion = Instrucciones.get(instruccionBits);
        instruccion.EjecutarInstruccion(instruccionBits, Registros);
    }
    
    @Override
    public void CambiarRegistros(Map<String, String> registros) {
        Registros = registros;
    }
    
    @Override
    public Map<String, String> ObtenerRegistros(){
        return Registros;
    }
    
    @Override
    public void AsignarBUS(BUS busAsignado) throws Exception{
        BUSAsignado = busAsignado;
    }
}
