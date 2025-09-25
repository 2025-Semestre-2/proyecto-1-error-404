/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistemasoperativos.pcvirtual.controlador;

import com.sistemasoperativos.pcvirtual.componentes.Almacenamiento;
import com.sistemasoperativos.pcvirtual.componentes.AlmacenamientoModelo1;
import com.sistemasoperativos.pcvirtual.componentes.BUS;
import com.sistemasoperativos.pcvirtual.componentes.BUSModelo1;
import com.sistemasoperativos.pcvirtual.componentes.CPU;
import com.sistemasoperativos.pcvirtual.componentes.CPUModelo2;
import com.sistemasoperativos.pcvirtual.componentes.Conversor;
import com.sistemasoperativos.pcvirtual.componentes.RAM;
import com.sistemasoperativos.pcvirtual.componentes.RAMModelo1;
import com.sistemasoperativos.pcvirtual.instrucciones.Instruccion;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author andrewdeni
 */
public class Controlador {
    
    public Controlador(){
        
    }
    
    public void CrearPC(int tamanoRAM, int tamanoAlmacenamiento){
        int direccionEscrituraAlmacenamiento = 0;
        Conversor conversor = new Conversor();
        RAM ram = new RAMModelo1(tamanoRAM);
        Almacenamiento almacenamiento = new AlmacenamientoModelo1(tamanoAlmacenamiento, conversor, direccionEscrituraAlmacenamiento);
        Map<String, Instruccion> instrucciones = new HashMap<>();
        Map<String, String> registros = CrearRegistros();
        CPU cpu = new CPUModelo2(instrucciones, registros);
        BUS bus = new BUSModelo1(ram, cpu);
    }
    
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
    private Map<String, String> CrearRegistros() {
        Map<String, String> registros = new HashMap<>();

        registros.put("PC", "00000000");
        registros.put("AC", "00000000");
        registros.put("IR", "00000000");
        registros.put("AX", "00000000");
        registros.put("BX", "00000000");
        registros.put("CX", "00000000");
        registros.put("DX", "00000000");
        registros.put("CP", "00000000");
        registros.put("SP", "00000000");

        return registros;
    }
    
    private void CrearInstrucciones(Map<String, Instruccion> instrucciones){
        
    }
}
