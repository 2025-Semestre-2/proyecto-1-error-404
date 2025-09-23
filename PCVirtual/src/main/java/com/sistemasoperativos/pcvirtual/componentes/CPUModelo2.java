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
    Map<String, Instruccion> Instrucciones;
    
    
    public CPUModelo2(Map<String, Instruccion> instrucciones, Map<String, String> registros){
        Instrucciones = instrucciones;
    }
    
    public void EjecutarInstruccion() throws Exception {
        
    }
    
    public boolean EscribirRegistro(String informacion)throws Exception{
        
    }
    
    public String LeerRegistro(String informacion) throws Exception{
        
    }
    
    public void AsignarBUS(BUS busAsignado) throws Exception{
        
    }
}
